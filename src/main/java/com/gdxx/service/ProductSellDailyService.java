package com.gdxx.service;

import java.util.Date;
import java.util.List;


import com.gdxx.entity.ProductSellDaily;

public interface ProductSellDailyService {
	/*
	 * ÿ�ն�ʱ�����е��̵���Ʒ��������ͳ��
	 */
	void dailyCalculate();

	List<ProductSellDaily> productSellDailyList(ProductSellDaily productSellDailyCondition, Date beginTime,
			Date endTime);
}
