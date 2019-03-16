package com.gdxx.service;

import java.util.Date;
import java.util.List;


import com.gdxx.entity.ProductSellDaily;

public interface ProductSellDailyService {
	/*
	 * 每日定时对所有店铺的商品销量进行统计
	 */
	void dailyCalculate();

	List<ProductSellDaily> productSellDailyList(ProductSellDaily productSellDailyCondition, Date beginTime,
			Date endTime);
}
