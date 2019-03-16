package com.gdxx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdxx.entity.CustomerComment;

public interface CustomerCommentDao {
	int insertCustomerComment(CustomerComment comment);

	int deleteCustomerCommentById(long CustomerCommentId);

	List<CustomerComment> queryCustomerCommentList(@Param("comment") CustomerComment comment,
			@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

	int queryCustomerCommentCount(@Param("comment") CustomerComment comment);

	Double queryAvgpointByProIdAndshopId(@Param("productId") Long productId, @Param("shopId") Long shopId);
}
