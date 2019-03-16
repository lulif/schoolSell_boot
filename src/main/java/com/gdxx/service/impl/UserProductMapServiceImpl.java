package com.gdxx.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdxx.dao.PersonInfoDao;
import com.gdxx.dao.ShopDao;
import com.gdxx.dao.UserProductMapDao;
import com.gdxx.dao.UserShopMapDao;
import com.gdxx.dto.UserProductMapExecution;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.Shop;
import com.gdxx.entity.UserProductMap;
import com.gdxx.entity.UserShopMap;
import com.gdxx.enums.UserProductMapStateEnum;
import com.gdxx.service.UserProductMapService;
import com.gdxx.util.PageCalculator;

@Service
public class UserProductMapServiceImpl implements UserProductMapService {
	@Autowired
	private UserProductMapDao userProductMapDao;
	@Autowired
	private UserShopMapDao userShopMapDao;
	@Autowired
	private ShopDao shopDao;
	@Autowired
	private PersonInfoDao personInfoDao;

	@Override
	public UserProductMapExecution listUserProductMap(UserProductMap userProductCondition, Integer pageIndex,
			Integer pageSize) {
		if (userProductCondition != null && pageIndex != null && pageSize != null) {
			int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
			List<UserProductMap> userProductMapList = userProductMapDao.queryUserProductMapList(userProductCondition,
					rowIndex, pageSize);
			int count = userProductMapDao.queryUserProductMapCount(userProductCondition);
			UserProductMapExecution upme = new UserProductMapExecution();
			upme.setCount(count);
			upme.setUserProductMapList(userProductMapList);
			return upme;
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public UserProductMapExecution addUserProductMap(UserProductMap userProductMap) throws RuntimeException {
		if (userProductMap != null && userProductMap.getUser().getUserId() != null
				&& userProductMap.getShop().getShopId() != null) {
			userProductMap.setCreateTime(new Date());
			try {
				int effNum1 = userProductMapDao.insertUserProductMap(userProductMap);
				if (effNum1 <= 0) {
					throw new RuntimeException("添加消费记录失败");
				}
				// 消费所得的积分
				if (userProductMap.getPoint() != null && userProductMap.getPoint() >= 0) {
					UserShopMap userShopMap = userShopMapDao.queryUserShopMap(userProductMap.getUser().getUserId(),
							userProductMap.getShop().getShopId());
					if (userShopMap != null) {
						if (userShopMap.getPoint() >= userProductMap.getPoint()) {
							userShopMap.setPoint(userShopMap.getPoint() + userProductMap.getPoint());
							// 消费之后更新顾客在此商店消费的积分
							int effNum2 = userShopMapDao.updateUserShopMapPoint(userShopMap);
							if (effNum2 <= 0) {
								throw new RuntimeException("更新积分信息失败");
							}
						}
					} else {
						// 在此店铺没消费过，添加用户积分信息
						userShopMap = compactUserShopMap4Add(userProductMap.getUser().getUserId(),
								userProductMap.getShop().getShopId(), userProductMap.getPoint());
						int effNum3 = userShopMapDao.insertUserShopMap(userShopMap);
						if (effNum3 <= 0) {
							throw new RuntimeException("积分信息创建失败");
						}
					}

				}
			} catch (Exception e) {
				throw new RuntimeException("消费时内部错误:" + e.toString());
			}
			return new UserProductMapExecution(UserProductMapStateEnum.SUCCESS, userProductMap);
		} else {
			return new UserProductMapExecution(UserProductMapStateEnum.NULL_USERPRODUCT_INFO);
		}

	}

	private UserShopMap compactUserShopMap4Add(Long userId, Long shopId, Integer point) {
		UserShopMap userShopMap = new UserShopMap();
		if (userId != null && shopId != null) {
			PersonInfo user = personInfoDao.queryPersonInfoById(userId);
			Shop shop = shopDao.queryByShopId(shopId);
			userShopMap.setUser(user);
			userShopMap.setShop(shop);
			userShopMap.setPoint(point);
			userShopMap.setCreateTime(new Date());
		}
		return userShopMap;
	}

	@Override
	public UserProductMap getUserProductMapById(long userProductId) {

		return userProductMapDao.queryUserProductMapById(userProductId);
	}

}
