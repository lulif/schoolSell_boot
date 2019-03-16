package com.gdxx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdxx.entity.Order;

public interface OrderDao {
	int insertOrder(@Param("order") Order order);

	List<Order> queryOrderListByIdAndStatus(@Param("shopId") Long shopId, @Param("userId") Long userId,
			@Param("orderStatus") Integer orderStatus);

	int updateExpireTime(String orderId);

	int updateOrderStatus(@Param("orderId") String orderId, @Param("orderStatus") Integer orderStatus);

	Order queryOrderByOrderId(String orderId);
}
