package com.gdxx.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdxx.dto.ShopExecution;
import com.gdxx.entity.Area;
import com.gdxx.entity.Shop;
import com.gdxx.entity.ShopCategory;
import com.gdxx.service.AreaService;
import com.gdxx.service.CustomerCommentService;
import com.gdxx.service.ShopCategoryService;
import com.gdxx.service.ShopService;
import com.gdxx.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private CustomerCommentService customerCommentService;

	@RequestMapping(value = "/shopspageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getlistshops(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long parentId = HttpServletRequestUtil.getLong(request, "parentId");
		List<ShopCategory> shopCategoryList = null;
		if (parentId != -1L) {
			ShopCategory shopCategoryCondition = new ShopCategory();
			ShopCategory parent = new ShopCategory();
			parent.setShopCategoryId(parentId);
			shopCategoryCondition.setParent(parent);
			try {
				shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		} else {
			try {
				shopCategoryList = shopCategoryService.getShopCategoryList(null);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}
		modelMap.put("shopCategoryList", shopCategoryList);
		try {
			List<Area> areaList = areaService.getAreaList();
			modelMap.put("success", true);
			modelMap.put("areaList", areaList);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}

	@RequestMapping(value = "/listshops", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShops(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		if (pageSize > -1 && pageIndex > -1) {
			int enableStatus = HttpServletRequestUtil.getInt(request, "enableStatus");
			long parentId = HttpServletRequestUtil.getLong(request, "parentId");
			long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
			int areaId = HttpServletRequestUtil.getInt(request, "areaId");
			String shopName = HttpServletRequestUtil.getString(request, "shopName");
			Shop shopCondition = getShopConditionForSearch(enableStatus, parentId, shopCategoryId, areaId, shopName);
			ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
			for (Shop shop : se.getShopList()) {
				Double sAvgPoint = customerCommentService.getAvgpointByProIdAndshopId(null, shop.getShopId());
				shop.setsAvgPoint(sAvgPoint);
			}
			modelMap.put("shopList", se.getShopList());
			modelMap.put("count", se.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex");
		}
		return modelMap;
	}

	private Shop getShopConditionForSearch(int enableStatus, long parentId, long shopCategoryId, int areaId,
			String shopName) {
		Shop shopCondition = new Shop();
		if (enableStatus != -1) {
			shopCondition.setEnableStatus(1);
		}
		if (parentId != -1L) {
			ShopCategory shopCategory = new ShopCategory();
			ShopCategory parent = new ShopCategory();
			parent.setShopCategoryId(parentId);
			shopCategory.setParent(parent);
			shopCondition.setShopCategory(shopCategory);
		}
		if (shopCategoryId != -1L) {
			ShopCategory shopCategory = new ShopCategory();
			shopCategory.setShopCategoryId(shopCategoryId);
			shopCondition.setShopCategory(shopCategory);
		}
		if (areaId != -1L) {
			Area area = new Area();
			area.setAreaId(areaId);
			shopCondition.setArea(area);
		}
		if (shopName != null) {
			shopCondition.setShopName(shopName);
		}
		return shopCondition;
	}
}
