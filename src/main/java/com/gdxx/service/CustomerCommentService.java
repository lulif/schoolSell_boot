package com.gdxx.service;

import com.gdxx.dto.CustomerCommentExecution;
import com.gdxx.entity.CustomerComment;

public interface CustomerCommentService {
	CustomerCommentExecution getCustomerCommentList(CustomerComment comment, Integer pageIndex, Integer pageSize);

	CustomerCommentExecution addCustomerComment(CustomerComment comment) throws RuntimeException;

	CustomerCommentExecution removeCustomerCommentById(Long CustomerCommentId) throws RuntimeException;

	Double getAvgpointByProIdAndshopId(Long productId, Long shopId);
}
