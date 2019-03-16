package com.gdxx.service;

import com.gdxx.dto.UserShopMapExecution;
import com.gdxx.entity.UserShopMap;

public interface UserShopMapService {
	UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition, Integer pageIndex, Integer pageSize);

	UserShopMapExecution getUserShopMapbyUserAndShop(Long userId, Long shopId);
}
