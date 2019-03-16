package com.gdxx.service;

import java.util.List;

import com.gdxx.Exceptions.ProductCategoryOperationException;
import com.gdxx.dto.ProductCategoryExecution;
import com.gdxx.entity.ProductCategory;

public interface ProductCategoryService {
	List<ProductCategory> getProductCategoryList(Long shopId);

	ProductCategoryExecution batchInsertProductCategoryList(List<ProductCategory> list)
			throws ProductCategoryOperationException;

	// �Ƚ�������µ���Ʒ��������Ϊnull���ٽ���ɾ��
	ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException;
}
