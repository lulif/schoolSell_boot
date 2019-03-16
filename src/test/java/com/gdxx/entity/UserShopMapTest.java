package com.gdxx.entity;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gdxx.dao.UserShopMapDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserShopMapTest {
	@Autowired
	private UserShopMapDao userShopMapDao;

	@Test
	public void test() {
		UserShopMap userShopMapCondition = new UserShopMap();
		PersonInfo p = new PersonInfo();
		p.setUserId(1742319L);
		Shop shop = new Shop();
		shop.setShopId(1L);
		userShopMapCondition.setUser(p);
		userShopMapCondition.setShop(shop);
		List<UserShopMap> list = userShopMapDao.queryUserShopMapList(userShopMapCondition, 0, 999);
		System.out.println(list.size());
		
		UserShopMap userShopMap=new UserShopMap();
		PersonInfo p1 = new PersonInfo();
		Shop shop1 = new Shop();
		userShopMap.setShop(shop1);
		userShopMap.setUser(p1);
		 userShopMap = userShopMapDao.queryUserShopMap(1742319L, 1L);
		 System.out.println(userShopMap.getUser().getUserId());
	}

}
