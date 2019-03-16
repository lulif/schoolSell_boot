package com.gdxx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.gdxx.Exceptions.ProductOperationException;
import com.gdxx.dao.ProductDao;
import com.gdxx.dao.ProductImgDao;
import com.gdxx.dto.ProductExecution;
import com.gdxx.entity.Product;
import com.gdxx.entity.ProductImg;
import com.gdxx.enums.ProductStateEnum;
import com.gdxx.service.ProductService;
import com.gdxx.util.ImageUtil;
import com.gdxx.util.PageCalculator;
import com.gdxx.util.PathUtil;

import ch.qos.logback.core.util.FileUtil;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;

	@Override
	@Transactional
	/*
	 * 1.处理缩略图，获取缩略图相对路径并赋值给product 2.往product写入商品信息，获取productId
	 * 3.结合productId批量处理商品详情图 4.将商品详情图列表批量插入表中
	 */
	public ProductExecution addProduct(Product product, CommonsMultipartFile thumbnail,
			List<CommonsMultipartFile> productImgs) throws ProductOperationException {
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			product.setEnableStatus(1);
			if (thumbnail != null) {
				addThumbnail(product, thumbnail);
			}
			try {
				int effNum = productDao.insertProduct(product);
				if (effNum <= 0) {
					throw new ProductOperationException("增加商品失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建商品失败" + e.toString());
			}
			if (productImgs != null && productImgs.size() > 0) {
				addProductImgList(product, productImgs);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	private void addProductImgList(Product product, List<CommonsMultipartFile> productImgs) {
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		for (CommonsMultipartFile cmf : productImgs) {
			String imgAddr = ImageUtil.generateThumbnail(cmf, dest, true);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
		}

		if (productImgList.size() > 0) {
			try {
				int effNum = productImgDao.batchInsertProductImg(productImgList);
				if (effNum <= 0) {
					throw new ProductOperationException("新增商品的详情图片失败");
				}
			} catch (Exception e) {
				e.getStackTrace();
				throw new ProductOperationException("新增商品的详情图片失败" + e.toString());
			}
		}

	}

	private void addThumbnail(Product product, CommonsMultipartFile thumbnail) {
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest, false);
		product.setImgAddr(shopImgAddr);
	}

	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductById(productId);
	}

	@Override
	@Transactional
	public ProductExecution modifyProduct(Product product, CommonsMultipartFile thumbnail,
			List<CommonsMultipartFile> productImgs) throws ProductOperationException {
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			product.setLastEditTime(new Date());
			if (thumbnail != null) {
				Product tempProduct = productDao.queryProductById(product.getProductId());
				if (tempProduct.getImgAddr() != null) {
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				addThumbnail(product, thumbnail);
			}

			if (productImgs != null && productImgs.size() > 0) {
				deleteProductImgs(product.getProductId());
				addProductImgList(product, productImgs);
			}

			try {
				int effNum = productDao.updateProduct(product);
				if (effNum <= 0) {
					throw new RuntimeException("更新商品信息失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS, product);
			} catch (Exception e) {
				e.getMessage();
				e.printStackTrace();
				throw new RuntimeException("更新商品信息失败:" + e.toString());
			}
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}

	}

	private void deleteProductImgs(Long productId) {
		List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
		for (ProductImg productImg : productImgList) {
			ImageUtil.deleteFileOrPath(productImg.getImgAddr());
		}
		productImgDao.deleteProductImgByProductId(productId);
	}

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		if (productCondition != null && rowIndex > -1 && pageSize > -1) {
			List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
			int count = productDao.queryProductCount(productCondition);
			ProductExecution pe = new ProductExecution(ProductStateEnum.SUCCESS, productList);
			pe.setCount(count);
			return pe;

		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

}
