package com.gdxx.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gdxx.entity.ProductSellDaily;
import com.gdxx.entity.Shop;
import com.gdxx.service.ProductSellDailyService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductSellDailyServiceImplTest {
	@Autowired
	private ProductSellDailyService productSellDailyService;

	@Test
	public void test() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		 Date d1 = dateFormat.parse("2019-1-21 00:00:00");
		
		 Date d2 = dateFormat.parse("2019-1-27 00:00:00");
		ProductSellDaily productSellDailyCondition = new ProductSellDaily();
		Shop shop=new Shop();
		shop.setShopId(1L);
		productSellDailyCondition.setShop( shop);
		List<ProductSellDaily> list = productSellDailyService.productSellDailyList(productSellDailyCondition, d1, d2);
		System.out.println(list.size());
	}

}
