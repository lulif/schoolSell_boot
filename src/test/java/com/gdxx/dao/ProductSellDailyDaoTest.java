package com.gdxx.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductSellDailyDaoTest {
	@Autowired
	private ProductSellDailyDao productSellDailyDao;
	@Test
	public void test() {
		productSellDailyDao.insertDefaultProductSellDaily();
	}

}
