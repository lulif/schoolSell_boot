package com.gdxx.service;

import com.gdxx.dto.OrderExecution;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.ReceivingAddress;

public interface OrderService {

	OrderExecution addOrderHadPayed(ReceivingAddress address, PersonInfo userId, double totalMon, String orderMsg);

	OrderExecution getOrderListByIdAndStatus(Long shopId,Long userId, Integer orderStatus);

	OrderExecution addOrderNotPayed(ReceivingAddress address, PersonInfo user, Double totalMon, String orderMsg);

	OrderExecution modifyOrderStatus(String orderId, String operationType);

	OrderExecution dealOrderHadCancel(PersonInfo user,String orderId);

	
}
