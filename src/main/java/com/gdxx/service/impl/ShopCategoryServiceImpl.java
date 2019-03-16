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
import com.gdxx.dao.ShopCategoryDao;
import com.gdxx.entity.ShopCategory;
import com.gdxx.service.ShopCategoryService;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedisStrings;
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	private static String SHOPCATEGORYLISTKEYS = "shopCategoryList";

	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
		String key = SHOPCATEGORYLISTKEYS;
		List<ShopCategory> shopCategoryList = null;
		ObjectMapper mapper = new ObjectMapper();
		if (shopCategoryCondition == null) {
			key = SHOPCATEGORYLISTKEYS + "_" + "parentIsNull";
		}
		if (shopCategoryCondition != null) {
			key = SHOPCATEGORYLISTKEYS + "_" + "parentIsNotNull";
		}
		if (shopCategoryCondition != null && shopCategoryCondition.getParent() != null
				&& shopCategoryCondition.getParent().getShopCategoryId() != -1L) {
			key = SHOPCATEGORYLISTKEYS + "_" + "parentIs" + shopCategoryCondition.getParent().getShopCategoryId();
		}
		if (!jedisKeys.exists(key)) {
			shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
			try {
				String jsonString = mapper.writeValueAsString(shopCategoryList);
				jedisStrings.set(key, jsonString);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} else {
			String jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
			try {
				shopCategoryList = mapper.readValue(jsonString, javaType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return shopCategoryList;
	}

}
