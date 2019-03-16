package com.gdxx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdxx.Exceptions.ProductCategoryOperationException;
import com.gdxx.dao.ProductCategoryDao;
import com.gdxx.dao.ProductDao;
import com.gdxx.dto.ProductCategoryExecution;
import com.gdxx.entity.ProductCategory;
import com.gdxx.enums.ProductCategoryStateEnum;
import com.gdxx.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Autowired
	private ProductDao productDao;

	@Override
	public List<ProductCategory> getProductCategoryList(Long shopId) {
		return productCategoryDao.queryProductCategoryList(shopId);
	}

	@Override
	@Transactional
	public ProductCategoryExecution batchInsertProductCategoryList(List<ProductCategory> list) {
		if (list != null && list.size() > 0) {
			try {
				int effNum = productCategoryDao.batchInsertProductCategoryList(list);
				if (effNum <= 0) {
					throw new ProductCategoryOperationException("商品类别新增失败");
				} else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
				}
			} catch (Exception e) {
				throw new ProductCategoryOperationException("batchInsertProductCategoryList" + e.getMessage());
			}
		} else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY);
		}
	}

	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) {
		try {
			int effectedNum2 = productDao.updateProductCategoryToNull(productCategoryId);
			int effectedNum1 = productCategoryDao.deleteProductCategory(productCategoryId, shopId);		
			if (effectedNum1 <= 0 || effectedNum2 <= 0) {
				throw new ProductCategoryOperationException("商品类别删除失败");
			} else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		} catch (ProductCategoryOperationException e) {
			throw new ProductCategoryOperationException("deleteProductCategory" + e.toString());
		}
	}
}
