package com.gdxx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdxx.cache.JedisUtil;
import com.gdxx.dao.HeadLineDao;
import com.gdxx.entity.HeadLine;
import com.gdxx.service.HeadLineService;

@Service
public class HeadLineServiceImpl implements HeadLineService {

	@Autowired
	private JedisUtil.Strings jedisStrings;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private HeadLineDao headLineDao;
	private static String HEADLINELISTKEYS = "headLineListKeys";

	@Override
	public List<HeadLine> headLineService(HeadLine headLineCondition) {
		String key = HEADLINELISTKEYS;
		List<HeadLine> headLineList = null;
		ObjectMapper mapper = new ObjectMapper();
		if (headLineCondition.getEnableStatus() != null) {
			key = key + "_" + headLineCondition.getEnableStatus();
		}
		if (!jedisKeys.exists(HEADLINELISTKEYS)) {
			headLineList = headLineDao.queryHeadLineList(headLineCondition);
			try {
				String jsonString = mapper.writeValueAsString(headLineList);
				jedisStrings.set(key, jsonString);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} else {
			String jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
			try {
				headLineList = mapper.readValue(jsonString, javaType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return headLineList;
	}

}
