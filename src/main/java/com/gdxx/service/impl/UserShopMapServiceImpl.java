package com.gdxx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdxx.dao.UserShopMapDao;
import com.gdxx.dto.UserShopMapExecution;
import com.gdxx.entity.UserShopMap;
import com.gdxx.enums.UserShopMapStateEnum;
import com.gdxx.service.UserShopMapService;
import com.gdxx.util.PageCalculator;

@Service
public class UserShopMapServiceImpl implements UserShopMapService {
	@Autowired
	private UserShopMapDao userShopMapDao;

	@Override
	public UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition, Integer pageIndex, Integer pageSize) {
		if (userShopMapCondition != null && pageIndex != null && pageSize != null) {
			int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
			List<UserShopMap> userShopMapList = userShopMapDao.queryUserShopMapList(userShopMapCondition, rowIndex,
					pageSize);
			int count = userShopMapDao.queryUserShopMapCount(userShopMapCondition);
			UserShopMapExecution usme = new UserShopMapExecution();
			usme.setUserShopMapList(userShopMapList);
			usme.setCount(count);
			return usme;
		} else {
			return null;
		}
	}

	@Override
	public UserShopMapExecution getUserShopMapbyUserAndShop(Long userId, Long shopId) {
		if (userId != null && shopId != null) {
			UserShopMap usm = userShopMapDao.queryUserShopMap(userId, shopId);
			if (usm != null) {
				return new UserShopMapExecution(UserShopMapStateEnum.SUCCESS, usm);
			}
		}
		return null;
	}
}
