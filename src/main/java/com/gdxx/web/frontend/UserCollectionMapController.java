package com.gdxx.web.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdxx.dto.UserCollectionMapExecution;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.Product;
import com.gdxx.entity.UserCollectionMap;
import com.gdxx.enums.UserCollectionMapStateEnum;
import com.gdxx.service.UserCollectionMapService;
import com.gdxx.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class UserCollectionMapController {
	@Autowired
	private UserCollectionMapService userCollectionMapService;

	@RequestMapping(value = "/addusercollectionmap", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addUserCollectionMap(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String userCollectionMapMsg = HttpServletRequestUtil.getString(request, "userCollectionMap");
		ObjectMapper mapper = new ObjectMapper();
		UserCollectionMap userCollectionMap = null;
		try {
			userCollectionMap = mapper.readValue(userCollectionMapMsg, UserCollectionMap.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		// PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
		PersonInfo user = new PersonInfo();
		user.setUserId(1742319L);
		userCollectionMap.setUser(user);

		if (userCollectionMap != null) {
			UserCollectionMapExecution ue = userCollectionMapService.addUserCollectionMap(userCollectionMap);
			if (ue.getStateInfo() == UserCollectionMapStateEnum.SUCCESS.getStateInfo()) {
				modelMap.put("success", true);
				return modelMap;
			}
			if (ue.getStateInfo() == UserCollectionMapStateEnum.HAD_COLLECTION.getStateInfo()) {
				modelMap.put("success", false);
				modelMap.put("had_collect", true);
			}
		}
		modelMap.put("success", false);
		return modelMap;
	}

	@RequestMapping(value = "/listusercollectionmap", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserCollectionMap(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String productName = HttpServletRequestUtil.getString(request, "productName");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		UserCollectionMap userCollectionMapCondition = new UserCollectionMap();
		// PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
		PersonInfo user = new PersonInfo();
		user.setUserId(1742319L);
		userCollectionMapCondition.setUser(user);
		if (productName != null) {
			Product p = new Product();
			p.setProductName(productName);
			userCollectionMapCondition.setProduct(p);
		}
		UserCollectionMapExecution ue = null;
		if (pageSize >-1  && pageIndex > -1) {
			ue = userCollectionMapService.getUserCollectionMapList(userCollectionMapCondition, pageIndex, pageSize);
			if (ue.getStateInfo() == UserCollectionMapStateEnum.SUCCESS.getStateInfo()) {
				modelMap.put("success", true);
				modelMap.put("userCollectionMapList", ue.getUserCollectionMapList());
				modelMap.put("count", ue.getCount());
				return modelMap;
			}
		}
		modelMap.put("success", false);
		modelMap.put("errMsg", ue.getStateInfo());
		return modelMap;

	}

	@RequestMapping(value = "/deleteusercollectionurl", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> deleteUserCollectionUrl(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long userCollectionId = HttpServletRequestUtil.getLong(request, "userCollectionId");
		if (userCollectionId != -1L) {
			UserCollectionMapExecution ue = userCollectionMapService.removeUserCollectionMap(userCollectionId);
			if (ue.getStateInfo() == UserCollectionMapStateEnum.SUCCESS.getStateInfo()) {
				modelMap.put("success", true);
				return modelMap;
			}
		}
		modelMap.put("success", false);
		return modelMap;
	}

}
