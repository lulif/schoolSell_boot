package com.gdxx.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gdxx.entity.Product;
import com.gdxx.entity.ProductImg;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDaoTest {
	@Autowired
	private ProductDao productDao;

	@Test
	public void test() {
		Product p=productDao.queryProductById(19);
		List<ProductImg> list=	p.getProductImgList();
		
		ProductImg p1= list.get(0);
		System.out.println("详情图片1："+p1.getImgAddr());
		
		ProductImg p2= list.get(1);
		System.out.println("详情图片2："+p2.getImgAddr());
	}

}
