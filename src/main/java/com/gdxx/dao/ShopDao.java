package com.gdxx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdxx.entity.Shop;

public interface ShopDao {
	// 新增店铺
	int insertShop(Shop shop);

	int updateShop(Shop shop);

	Shop queryByShopId(long shopId);

	/*
	 * 分页查询：可输入店铺名，店铺状态，店铺类别，区域id，owner
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	// 返回queryShopList的总数
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
}
