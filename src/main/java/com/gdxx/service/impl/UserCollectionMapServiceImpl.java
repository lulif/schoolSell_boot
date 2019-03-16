package com.gdxx.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdxx.dao.PersonInfoDao;
import com.gdxx.dao.ProductDao;
import com.gdxx.dao.ShopDao;
import com.gdxx.dao.UserCollectionMapDao;
import com.gdxx.dto.UserCollectionMapExecution;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.Product;
import com.gdxx.entity.Shop;
import com.gdxx.entity.UserCollectionMap;
import com.gdxx.entity.UserProductMap;
import com.gdxx.enums.UserCollectionMapStateEnum;
import com.gdxx.service.UserCollectionMapService;
import com.gdxx.util.PageCalculator;

@Service
public class UserCollectionMapServiceImpl implements UserCollectionMapService {
	@Autowired
	private UserCollectionMapDao userCollectionMapDao;
	@Autowired
	private PersonInfoDao personInfoDao;
	@Autowired
	private ShopDao shopDao;
	@Autowired
	private ProductDao productDao;

	@Override
	public UserCollectionMapExecution getUserCollectionMapList(UserCollectionMap userCollectionCondition,
			Integer pageIndex, Integer pageSize) {
		if (userCollectionCondition != null && pageIndex != null && pageSize != null) {
			int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
			List<UserCollectionMap> userCollectionMapList = userCollectionMapDao
					.queryUserCollectionMapList(userCollectionCondition, rowIndex, pageSize);
			int count = userCollectionMapDao.queryUserCollectionMapcount(userCollectionCondition);
			UserCollectionMapExecution ue = new UserCollectionMapExecution();
			ue.setCount(count);
			ue.setUserCollectionMapList(userCollectionMapList);
			ue.setStateInfo(UserCollectionMapStateEnum.SUCCESS.getStateInfo());
			return ue;
		}
		return null;
	}

	@Override
	@Transactional
	public UserCollectionMapExecution addUserCollectionMap(UserCollectionMap userCollectionMap) {
		if (userCollectionMap.getUser() != null && userCollectionMap.getProduct() != null
				&& userCollectionMap.getShop() != null) {
			userCollectionMap.setCreateTime(new Date());
			try {
				Long userId = userCollectionMap.getUser().getUserId();
				Long shopId = userCollectionMap.getShop().getShopId();
				Long productId = userCollectionMap.getProduct().getProductId();
				PersonInfo p = personInfoDao.queryPersonInfoById(userId);
				Shop shop = shopDao.queryByShopId(shopId);
				Product product = productDao.queryProductById(productId);
				if (p != null && shop != null && product != null) {
					UserCollectionMap tempUserCollectionMap = userCollectionMapDao.queryUserCollectionById(userId,
							shopId, productId, null);
					if (tempUserCollectionMap != null) {
						return new UserCollectionMapExecution(UserCollectionMapStateEnum.HAD_COLLECTION);
					}
					int effNum = userCollectionMapDao.insertUserCollection(userCollectionMap);
					if (effNum <= 0) {
						return new UserCollectionMapExecution(UserCollectionMapStateEnum.INNER_ERROR);
					}
				} else {
					return new UserCollectionMapExecution(UserCollectionMapStateEnum.NULL_USERCOLLECTION_INFO);
				}

			} catch (Exception e) {
				e.toString();
				throw new RuntimeException();
			}
			return new UserCollectionMapExecution(UserCollectionMapStateEnum.SUCCESS);
		} else {
			return new UserCollectionMapExecution(UserCollectionMapStateEnum.NULL_USERCOLLECTION_INFO);
		}
	}

	@Override
	@Transactional
	public UserCollectionMapExecution removeUserCollectionMap(Long userCollectionId) {
		if (userCollectionId != null) {
			try {
				UserCollectionMap userCollectionMap = userCollectionMapDao.queryUserCollectionById(null, null, null,
						userCollectionId);
				if (userCollectionMap == null) {
					return new UserCollectionMapExecution(UserCollectionMapStateEnum.INNER_ERROR);
				}

				int effNum = userCollectionMapDao.deleteUserCollection(userCollectionId);
				if (effNum <= 0) {
					return new UserCollectionMapExecution(UserCollectionMapStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new RuntimeException("抛出异常失败,取消收藏失敗");
			}
			return new UserCollectionMapExecution(UserCollectionMapStateEnum.SUCCESS);
		} else {
			return new UserCollectionMapExecution(UserCollectionMapStateEnum.NULL_USERCOLLECTION_ID);
		}
	}

}
