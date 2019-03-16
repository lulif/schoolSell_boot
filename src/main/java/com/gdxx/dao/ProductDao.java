package com.gdxx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdxx.entity.Product;

public interface ProductDao {
	int insertProduct(Product product);

	Product queryProductById(long productId);

	int updateProduct(Product product);

	List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	int queryProductCount(@Param("productCondition") Product productCondition);

	// 若删除的商品类别为此商品的类别，则把此商品的类别置为null
	int updateProductCategoryToNull(long productCategoryId);
}
