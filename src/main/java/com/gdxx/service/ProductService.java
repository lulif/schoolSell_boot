package com.gdxx.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.gdxx.Exceptions.ProductOperationException;
import com.gdxx.dto.ProductExecution;
import com.gdxx.entity.Product;

public interface ProductService {
	// 添加商品和缩略图处理
	ProductExecution addProduct(Product product, CommonsMultipartFile thumbnail, List<CommonsMultipartFile> productImgs)
			throws ProductOperationException;

	Product getProductById(long productId);

	ProductExecution modifyProduct(Product product, CommonsMultipartFile thumbnail,
			List<CommonsMultipartFile> productImgs) throws ProductOperationException;
	
	ProductExecution getProductList(Product productCondition,int rowIndex,int pageSize);
	
}
