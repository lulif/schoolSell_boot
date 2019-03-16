package com.gdxx.dao;

import java.util.List;

import com.gdxx.entity.OrderDetail;

public interface OrderDetailDao {
	int insertOrderDetailList(List<OrderDetail> orderDetailList);
}
