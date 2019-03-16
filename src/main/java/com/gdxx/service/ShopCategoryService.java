package com.gdxx.service;

import java.util.List;

import com.gdxx.entity.ShopCategory;

public interface ShopCategoryService {
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
