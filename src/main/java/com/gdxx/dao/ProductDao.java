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

	// ��ɾ������Ʒ���Ϊ����Ʒ�������Ѵ���Ʒ�������Ϊnull
	int updateProductCategoryToNull(long productCategoryId);
}
