package com.gdxx.service;

import java.util.List;

import com.gdxx.Exceptions.ProductCategoryOperationException;
import com.gdxx.dto.ProductCategoryExecution;
import com.gdxx.entity.ProductCategory;

public interface ProductCategoryService {
	List<ProductCategory> getProductCategoryList(Long shopId);

	ProductCategoryExecution batchInsertProductCategoryList(List<ProductCategory> list)
			throws ProductCategoryOperationException;

	// 先将此类别下的商品里的类别置为null，再将其删除
	ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException;
}
