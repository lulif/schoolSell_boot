package com.gdxx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdxx.entity.ShopAuthMap;

public interface ShopAuthMapDao {
	/**
	 * ��ҳ�г������������Ȩ��Ϣ
	 * 
	 * @param shopId
	 * @param beginIndex
	 * @param pageSize
	 * @return
	 */
	List<ShopAuthMap> queryShopAuthMapListByShopId(@Param("shopId") long shopId, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	/**
	 * ��ȡ��Ȩ����
	 * 
	 * @param shopId
	 * @return
	 */
	int queryShopAuthCountByShopId(@Param("shopId") long shopId);

	/**
	 * ����һ���������Ա����Ȩ��ϵ
	 * 
	 * @param shopAuthMap
	 * @return effectedNum
	 */
	int insertShopAuthMap(ShopAuthMap shopAuthMap);

	/**
	 * ������Ȩ��Ϣ
	 * 
	 * @param shopAuthMap
	 * @return
	 */
	int updateShopAuthMap(ShopAuthMap shopAuthMap);

	/**
	 * ��ĳԱ����Ȩ
	 * 
	 * @param employeeId
	 * @param shopId
	 * @return effectedNum
	 */
	int deleteShopAuthMap(long shopAuthMapId);

	/**
	 * 
	 * @param shopAuthId
	 * @return
	 */
	ShopAuthMap queryShopAuthMapById(Long shopAuthId);
}
