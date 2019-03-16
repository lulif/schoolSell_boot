package com.gdxx.service;

import java.io.File;
import java.io.InputStream;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.gdxx.Exceptions.ShopOperationException;
import com.gdxx.dto.ShopExecution;
import com.gdxx.entity.Shop;

public interface ShopService {

	ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg);
	
	Shop getShopByShopId(long shopId);
	
	ShopExecution modifyShop(Shop shop,CommonsMultipartFile shopImgInputStream)throws ShopOperationException;
	
	ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
}
