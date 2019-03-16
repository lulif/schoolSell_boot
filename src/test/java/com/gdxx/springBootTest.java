package com.gdxx;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gdxx.dao.ShopAuthMapDao;
import com.gdxx.entity.ShopAuthMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class springBootTest {
	@Autowired
	private ShopAuthMapDao shopAuthMapDao;

	@Test
	public void areaTest() {
		List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(1, 0, 10);
		for (ShopAuthMap t : shopAuthMapList) {
			System.out.println(t.toString());
		}

	}
}
