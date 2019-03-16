package com.gdxx.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdxx.cache.JedisUtil;
import com.gdxx.dao.AreaDao;
import com.gdxx.entity.Area;
import com.gdxx.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private JedisUtil.Strings jedisStrings;
	@Autowired
	private JedisUtil.Keys jedisKeys;

	private static String AREALISTKEYS = "areaList";

	@Override
	public List<Area> getAreaList() {
		// 定义key
		String key = AREALISTKEYS;
		// 接查出来的东西
		List<Area> areaList = null;
		// 用来转换对象和其他类型用的
		ObjectMapper mapper = new ObjectMapper();
		if (!jedisKeys.exists(key)) {
			System.out.println(jedisKeys);
			areaList = areaDao.queryArea();
			try {
				// 将查出来的东西转为String
				String jsonString = mapper.writeValueAsString(areaList);
				jedisStrings.set(key, jsonString);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} else {
			String jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
			try {
				areaList = mapper.readValue(jsonString, javaType);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return areaList;
	}

}
