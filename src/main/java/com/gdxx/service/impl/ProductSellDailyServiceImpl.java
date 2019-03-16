package com.gdxx.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdxx.dao.ProductSellDailyDao;
import com.gdxx.entity.ProductSellDaily;
import com.gdxx.service.ProductSellDailyService;

@Service
public class ProductSellDailyServiceImpl implements ProductSellDailyService {
	private static final Logger log = LoggerFactory.getLogger(ProductSellDaily.class);
	@Autowired
	private ProductSellDailyDao productSellDailyDao;

	@Override
	public void dailyCalculate() {
		log.info("Quartz is Running");
		productSellDailyDao.insertProductSellDaily();
		productSellDailyDao.insertDefaultProductSellDaily();
	}

	@Override
	public List<ProductSellDaily> productSellDailyList(ProductSellDaily productSellDailyCondition, Date beginTime,
			Date endTime) {
		return productSellDailyDao.queryProductSellDaily(productSellDailyCondition, beginTime, endTime);

	}

}
