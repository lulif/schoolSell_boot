package com.gdxx.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdxx.dto.ProductExecution;
import com.gdxx.entity.Product;
import com.gdxx.entity.ProductCategory;
import com.gdxx.entity.Shop;
import com.gdxx.service.CustomerCommentService;
import com.gdxx.service.ProductCategoryService;
import com.gdxx.service.ProductService;
import com.gdxx.service.ShopService;
import com.gdxx.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CustomerCommentService customerCommentService;

	@RequestMapping(value = "/shopdetailpageinfo")
	@ResponseBody
	private Map<String, Object> getShopdetail(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopid");
		if (shopId > -1) {
			Shop shop = shopService.getShopByShopId(shopId);
			List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(shopId);
			modelMap.put("success", true);
			modelMap.put("shop", shop);
			modelMap.put("productCategoryList", productCategoryList);
		} else {
			modelMap.put("success", true);
			modelMap.put("errMsg", "shopIdÎª¿Õ");
		}
		return modelMap;
	}

	@RequestMapping(value = "/productinfobyshop")
	@ResponseBody
	private Map<String, Object> getProductInfoByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		long shopId = HttpServletRequestUtil.getLong(request, "shopid");
		int enableStatus = HttpServletRequestUtil.getInt(request, "enableStatus");
		if ((pageIndex > -1) && (pageSize > -1) && (shopId > -1)) {
			long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
			String productName = HttpServletRequestUtil.getString(request, "productName");
			Product productCondition = getProductConditionForSearch(productCategoryId, productName, shopId,
					enableStatus);
			ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
			for (Product product : pe.getProductList()) {
				Double avgPoint = customerCommentService.getAvgpointByProIdAndshopId(product.getProductId(), shopId);
				product.setAvgPoint(avgPoint);
			}
			modelMap.put("productList", pe.getProductList());
			modelMap.put("count", pe.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	private Product getProductConditionForSearch(long productCategoryId, String productName, long shopId,
			int enableStatus) {
		Product product = new Product();
		if (enableStatus != -1) {
			product.setEnableStatus(enableStatus);
		}
		if (productCategoryId != -1L) {
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			product.setProductCategory(productCategory);
		}
		if (productName != null) {
			product.setProductName(productName);
		}
		if (shopId > -1L) {
			Shop shop = new Shop();
			shop.setShopId(shopId);
			product.setShop(shop);
		}
		return product;
	}
}
