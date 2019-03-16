package com.gdxx.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdxx.entity.ProductSellDaily;

public interface ProductSellDailyDao {
	List<ProductSellDaily> queryProductSellDaily(
			@Param("productSellDailyCondition") ProductSellDaily productSellDailyCondition,
			@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

	int insertProductSellDaily();

	// 平台当天没销售，补全信息，默认为0
	int insertDefaultProductSellDaily();
}
