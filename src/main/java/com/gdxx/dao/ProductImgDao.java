package com.gdxx.dao;

import java.util.List;

import com.gdxx.entity.ProductImg;

public interface ProductImgDao {
	int batchInsertProductImg(List<ProductImg> productImgList);

	int deleteProductImgByProductId(Long productId);

	List<ProductImg> queryProductImgList(Long productId);
}
