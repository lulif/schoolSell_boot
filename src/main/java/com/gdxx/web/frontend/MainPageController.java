package com.gdxx.web.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdxx.entity.HeadLine;
import com.gdxx.entity.ShopCategory;
import com.gdxx.service.HeadLineService;
import com.gdxx.service.ShopCategoryService;
import com.gdxx.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class MainPageController {
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private HeadLineService headLineService;

	@RequestMapping(value = "/mainpageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> mainPageInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<HeadLine> headLineList = new ArrayList<HeadLine>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		try {
			//²éparent is null
			shopCategoryList = shopCategoryService.getShopCategoryList(null);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			HeadLine headLineCondition = new HeadLine();
			headLineCondition.setEnableStatus(1);
			headLineList = headLineService.headLineService(headLineCondition);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		modelMap.put("success", true);
		modelMap.put("headLineList", headLineList);
		modelMap.put("shopCategoryList", shopCategoryList);
		return modelMap;
	}
}
