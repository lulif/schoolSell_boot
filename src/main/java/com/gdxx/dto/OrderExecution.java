package com.gdxx.dto;

import java.util.List;
import java.util.Map;

import com.gdxx.entity.Order;
import com.gdxx.entity.Product;
import com.gdxx.enums.OrderStateEnum;

public class OrderExecution {
	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 店铺数量
	private int count;

	// 操作的Order（增删改商品的时候用）
	private Order Order;

	// 获取的Order列表(查询商品列表的时候用)
	private List<Order> OrderList;

	private Map<String, List<Product>> orderIdProductListMap;

	public OrderExecution() {
	}

	// 失败的构造器
	public OrderExecution(OrderStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 成功的构造器
	public OrderExecution(OrderStateEnum stateEnum, Order Order) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.Order = Order;
	}

	// 成功的构造器
	public OrderExecution(OrderStateEnum stateEnum, List<Order> OrderList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.OrderList = OrderList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Order getOrder() {
		return Order;
	}

	public void setOrder(Order Order) {
		this.Order = Order;
	}

	public List<Order> getOrderList() {
		return OrderList;
	}

	public void setOrderList(List<Order> OrderList) {
		this.OrderList = OrderList;
	}

	public Map<String, List<Product>> getOrderIdProductListMap() {
		return orderIdProductListMap;
	}

	public void setOrderIdProductListMap(Map<String, List<Product>> orderIdProductListMap) {
		this.orderIdProductListMap = orderIdProductListMap;
	}

}
