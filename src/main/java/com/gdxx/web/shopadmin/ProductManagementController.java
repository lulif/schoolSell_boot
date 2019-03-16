package com.gdxx.web.shopadmin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdxx.dto.ProductCategoryExecution;
import com.gdxx.dto.ProductExecution;
import com.gdxx.entity.Product;
import com.gdxx.entity.ProductCategory;
import com.gdxx.entity.Shop;
import com.gdxx.enums.ProductCategoryStateEnum;
import com.gdxx.enums.ProductStateEnum;
import com.gdxx.service.ProductCategoryService;
import com.gdxx.service.ProductService;
import com.gdxx.util.CodeUtil;
import com.gdxx.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private ProductService productService;
	// 上传详情图最大数量
	private static final int IMAGEMAXCOUNT = 6;

	@RequestMapping(value = "/getproductcategorylist")
	@ResponseBody
	private Map<String, Object> getProductCategoryList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		List<ProductCategory> productCategoryList = null;
		if (currentShop != null && currentShop.getShopId() > 0) {

			productCategoryList = productCategoryService.getProductCategoryList(currentShop.getShopId());
			modelMap.put("success", true);
			modelMap.put("productCategoryList", productCategoryList);
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", ProductCategoryStateEnum.INNER_ERROR.getStateInfo());
			return modelMap;
		}
	}

	@RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		for (ProductCategory pc : productCategoryList) {
			pc.setShopId(currentShop.getShopId());
			pc.setCreateTime(new Date());
			pc.setLastEditTime(new Date());
		}
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				ProductCategoryExecution pe = productCategoryService
						.batchInsertProductCategoryList(productCategoryList);
				if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);

				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "至少输入一个商品类别");
		}
		return modelMap;
	}

	@RequestMapping(value = "/deleteproductcategory", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> deleteProductCategory(Long productCategoryId, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();

		if (productCategoryId != null && productCategoryId > 0) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId,
						currentShop.getShopId());
				if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少选择一个商品类别");
		}
		return modelMap;
	}

	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入有误！");
			return modelMap;
		}
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		try {
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile productImg = null;
		List<CommonsMultipartFile> productDetailImgList = new ArrayList<CommonsMultipartFile>();
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			productImg = handleImage(request, productDetailImgList);
		} else {
			modelMap.put("success", false);
			modelMap.put("success", "上传图片不能为空");
			return modelMap;
		}

		if (product != null && productImg != null && productDetailImgList.size() > 0) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				product.setShop(currentShop);
				ProductExecution pe = productService.addProduct(product, productImg, productDetailImgList);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getState());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
		}
		return modelMap;
	}

	@RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getProductById(@RequestParam Long productid) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (productid > 0) {
			Product product = productService.getProductById(productid);
			List<ProductCategory> productCategoryList = productCategoryService
					.getProductCategoryList(product.getShop().getShopId());
			modelMap.put("success", true);
			modelMap.put("product", product);
			modelMap.put("productCategoryList", productCategoryList);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "productId为空");
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 修改(有验证码的)还是下架(没验证码的)
		boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
		if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入有误！");
			return modelMap;
		}
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		try {
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile productImg = null;
		List<CommonsMultipartFile> productDetailImgList = new ArrayList<CommonsMultipartFile>();
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			productImg = handleImage(request, productDetailImgList);
		}
		if (product != null) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				product.setShop(currentShop);
				ProductExecution pe = productService.modifyProduct(product, productImg, productDetailImgList);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getState());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
		}
		return modelMap;

	}

	@RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getProductListByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		if (pageSize > -1 && pageIndex > -1 && currentShop != null && currentShop.getShopId() != null) {
			long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");

			String productName = HttpServletRequestUtil.getString(request, "productName");

			Product productCondition = new Product();
			productCondition.setShop(currentShop);
			if (productCategoryId != -1L) {
				ProductCategory productCategory = new ProductCategory();
				productCategory.setProductCategoryId(productCategoryId);
				productCondition.setProductCategory(productCategory);
			}
			productCondition.setProductName(productName);

			ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);

			modelMap.put("success", true);
			modelMap.put("productList", pe.getProductList());
			modelMap.put("count", pe.getCount());
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	private CommonsMultipartFile handleImage(HttpServletRequest request,
			List<CommonsMultipartFile> productDetailImgList) {
		CommonsMultipartFile productImg;
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		productImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg");
		for (int i = 0; i < IMAGEMAXCOUNT; i++) {
			CommonsMultipartFile productDetailImg = (CommonsMultipartFile) multipartHttpServletRequest
					.getFile("productDetailImg" + i);
			if (productDetailImg != null) {
				productDetailImgList.add(productDetailImg);
			}

		}
		return productImg;
	}

}
