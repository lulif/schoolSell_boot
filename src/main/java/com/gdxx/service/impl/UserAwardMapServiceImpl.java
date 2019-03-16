package com.gdxx.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdxx.dao.UserAwardMapDao;
import com.gdxx.dao.UserShopMapDao;
import com.gdxx.dto.UserAwardMapExecution;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.UserAwardMap;
import com.gdxx.entity.UserShopMap;
import com.gdxx.enums.UserAwardMapStateEnum;
import com.gdxx.service.UserAwardMapService;
import com.gdxx.util.PageCalculator;

@Service
public class UserAwardMapServiceImpl implements UserAwardMapService {
	@Autowired
	private UserAwardMapDao userAwardMapDao;
	@Autowired
	private UserShopMapDao userShopMapDao;

	@Override
	public UserAwardMapExecution listUserAwardMap(UserAwardMap userAwardCondition, Integer pageIndex,
			Integer pageSize) {
		if (userAwardCondition != null && pageIndex != null && pageSize != null) {
			int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
			List<UserAwardMap> userAwardMapList = userAwardMapDao.queryUserAwardMapList(userAwardCondition, rowIndex,
					pageSize);
			int count = userAwardMapDao.queryUserAwardMapCount(userAwardCondition);
			UserAwardMapExecution ue = new UserAwardMapExecution();
			ue.setUserAwardMapList(userAwardMapList);
			ue.setCount(count);
			return ue;
		} else {
			return null;
		}
	}

	@Override
	public UserAwardMap getUserAwardMapById(long userAwardMapId) {
		return userAwardMapDao.queryUserAwardMapById(userAwardMapId);
	}

	@Override
	@Transactional
	public UserAwardMapExecution addUserAwardMap(UserAwardMap userAwardMap) throws RuntimeException {
		if (userAwardMap != null && userAwardMap.getUser().getUserId() != null
				&& userAwardMap.getUser().getUserId() != null) {
			userAwardMap.setCreateTime(new Date());
			PersonInfo operator = new PersonInfo();
			operator.setUserId(1L);
			userAwardMap.setOperator(operator);
			try {
				// 兑换物品所需的积分不等于null且大于0
				if (userAwardMap.getPoint() != null && userAwardMap.getPoint() > 0) {

					UserShopMap userShopMap = userShopMapDao.queryUserShopMap(userAwardMap.getUser().getUserId(),
							userAwardMap.getShop().getShopId());
					// 这位顾客是否在这家店
					if (userShopMap != null) {
						if (userShopMap.getPoint() >= userAwardMap.getPoint()) {
							userShopMap.setPoint(userShopMap.getPoint() - userAwardMap.getPoint());
							// 兑换完之后更新 顾客在此店铺的积分数
							int effNum1 = userShopMapDao.updateUserShopMapPoint(userShopMap);
							int effNum2 = userAwardMapDao.insertUserAwardMap(userAwardMap);
							if (effNum2 <= 0) {
								throw new RuntimeException("领取奖励失败");
							}
							if (effNum1 <= 0) {
								throw new RuntimeException("更新积分信息失败");
							}

						} else {
							throw new RuntimeException("积分不足无法领取");
						}
					} else {
						throw new RuntimeException("在本店铺没有积分，无法对换奖品");
					}
				}
			} catch (Exception e) {
				throw new RuntimeException("领取奖励失败:" + e.toString());
			}
			return new UserAwardMapExecution(UserAwardMapStateEnum.SUCCESS, userAwardMap);
		} else {
			return new UserAwardMapExecution(UserAwardMapStateEnum.NULL_USERAWARD_INFO);
		}

	}

	@Override
	@Transactional
	public UserAwardMapExecution modifyUserAwardMap(UserAwardMap userAwardMap) throws RuntimeException {

		if (userAwardMap != null && userAwardMap.getUsedStatus() != null
				&& userAwardMap.getAward().getAwardId() != null) {
			try {
				// 修改可用状态
				int effectedNum = userAwardMapDao.updateUserAwardMap(userAwardMap);
				if (effectedNum <= 0) {
					return new UserAwardMapExecution(UserAwardMapStateEnum.INNER_ERROR);
				} else {
					return new UserAwardMapExecution(UserAwardMapStateEnum.SUCCESS, userAwardMap);
				}
			} catch (Exception e) {
				throw new RuntimeException("modifyUserAwardMap error: " + e.getMessage());
			}
		} else {
			return new UserAwardMapExecution(UserAwardMapStateEnum.NULL_USERAWARD_ID);
		}
	}

}
