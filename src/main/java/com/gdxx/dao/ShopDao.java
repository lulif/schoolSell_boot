package com.gdxx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdxx.entity.Shop;

public interface ShopDao {
	// ��������
	int insertShop(Shop shop);

	int updateShop(Shop shop);

	Shop queryByShopId(long shopId);

	/*
	 * ��ҳ��ѯ�������������������״̬�������������id��owner
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	// ����queryShopList������
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
}
