package com.gdxx.service;

import com.gdxx.dto.ShopAuthMapExecution;
import com.gdxx.entity.ShopAuthMap;

public interface ShopAuthMapService {
	/**
	 * 
	 * @param shopId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ShopAuthMapExecution listShopAuthMapByShopId(Long shopId, Integer pageIndex, Integer pageSize);

	/**
	 * 
	 * @param shopAuthMap
	 * @return
	 * @throws RuntimeException
	 */
	ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap) throws RuntimeException;

	/**
	 * 更新授权信息，包括职位等
	 * 
	 * @param shopAuthId
	 * @param title
	 * @param titleFlag
	 * @param enableStatus
	 * @return
	 * @throws RuntimeException
	 */
	ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) throws RuntimeException;

	/**
	 * 
	 * @param shopAuthMapId
	 * @return
	 * @throws RuntimeException
	 */
	int removeShopAuthMap(Long shopAuthMapId) throws RuntimeException;

	/**
	 * 
	 * @param shopAuthId
	 * @return
	 */
	ShopAuthMap getShopAuthMapById(Long shopAuthId);

}
