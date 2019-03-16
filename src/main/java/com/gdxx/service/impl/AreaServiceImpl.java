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
		// ����key
		String key = AREALISTKEYS;
		// �Ӳ�����Ķ���
		List<Area> areaList = null;
		// ����ת����������������õ�
		ObjectMapper mapper = new ObjectMapper();
		if (!jedisKeys.exists(key)) {
			System.out.println(jedisKeys);
			areaList = areaDao.queryArea();
			try {
				// ��������Ķ���תΪString
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
