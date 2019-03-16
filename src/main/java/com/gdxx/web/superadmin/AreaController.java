package com.gdxx.web.superadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdxx.entity.Area;
import com.gdxx.service.AreaService;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/superadmin")
public class AreaController {
	Logger logger = (Logger) LoggerFactory.getLogger(AreaController.class);
	@Autowired
	private AreaService areaService;

	@RequestMapping(value = "/listarea", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listArea() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			areaList = areaService.getAreaList();
			// 前端用easyUI 所以用 rows和total
			modelMap.put("rows", areaList);
			modelMap.put("total", areaList.size());
		} catch (Exception e) {
			e.getMessage();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	private String testa() {
		return "/index";
	}
}
