package com.gdxx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdxx.entity.ProductCategory;

public interface ProductCategoryDao {
	List<ProductCategory> queryProductCategoryList(long shopId);
	//批量新增商品类别
	int batchInsertProductCategoryList(List<ProductCategory> productCategoryList);
	
	int deleteProductCategory(@Param("productCategoryId") long productCategoryId,@Param("shopId") long shopId);
}
