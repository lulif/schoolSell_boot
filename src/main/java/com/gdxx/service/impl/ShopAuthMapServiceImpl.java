package com.gdxx.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdxx.dao.ShopAuthMapDao;
import com.gdxx.dto.ShopAuthMapExecution;
import com.gdxx.entity.ShopAuthMap;
import com.gdxx.enums.ShopAuthMapStateEnum;
import com.gdxx.service.ShopAuthMapService;
import com.gdxx.util.PageCalculator;
@Service
public class ShopAuthMapServiceImpl implements ShopAuthMapService {
	@Autowired
	private ShopAuthMapDao shopAuthMapDao;

	@Override
	public ShopAuthMapExecution listShopAuthMapByShopId(Long shopId, Integer pageIndex, Integer pageSize) {
		if (shopId != null && pageIndex != null && pageSize != null) {
			int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
			List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(shopId, rowIndex, pageSize);
			int count = shopAuthMapDao.queryShopAuthCountByShopId(shopId);
			ShopAuthMapExecution same = new ShopAuthMapExecution();
			same.setShopAuthMapList(shopAuthMapList);
			same.setCount(count);
			return same;
		} else {
			return null;
		}

	}

	@Override
	public ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap) throws RuntimeException {

		if (shopAuthMap != null && shopAuthMap.getEmployee().getUserId() != null
				&& shopAuthMap.getShop().getShopId() != null) {
			shopAuthMap.setCreateTime(new Date());
			shopAuthMap.setLastEditTime(new Date());
			shopAuthMap.setEnableStatus(1);
			try {
				int effNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
				if (effNum <= 0) {
					throw new RuntimeException("添加授权失败");
				}
				return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS, shopAuthMap);
			} catch (Exception e) {
				throw new RuntimeException("添加授权失败:" + e.toString());
			}
		} else {
			return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_INFO);
		}
	}

	@Override
	public ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) throws RuntimeException {

		if (shopAuthMap == null || shopAuthMap.getShopAuthId() == null) {
			return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_ID);
		} else {
			try {
				shopAuthMap.setLastEditTime(new Date());
				int effectedNum = shopAuthMapDao.updateShopAuthMap(shopAuthMap);
				if (effectedNum <= 0) {
					return new ShopAuthMapExecution(ShopAuthMapStateEnum.INNER_ERROR);
				} else {// 创建成功
					return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS, shopAuthMap);
				}
			} catch (Exception e) {
				throw new RuntimeException("updateShopByOwner error: " + e.getMessage());
			}
		}
	}

	@Override
	public int removeShopAuthMap(Long shopAuthMapId) throws RuntimeException {

		return shopAuthMapDao.deleteShopAuthMap(shopAuthMapId);
	}

	@Override
	public ShopAuthMap getShopAuthMapById(Long shopAuthId) {

		return shopAuthMapDao.queryShopAuthMapById(shopAuthId);
	}

}
