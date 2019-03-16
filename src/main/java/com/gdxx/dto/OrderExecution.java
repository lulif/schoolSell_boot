package com.gdxx.dto;

import java.util.List;
import java.util.Map;

import com.gdxx.entity.Order;
import com.gdxx.entity.Product;
import com.gdxx.enums.OrderStateEnum;

public class OrderExecution {
	// ���״̬
	private int state;

	// ״̬��ʶ
	private String stateInfo;

	// ��������
	private int count;

	// ������Order����ɾ����Ʒ��ʱ���ã�
	private Order Order;

	// ��ȡ��Order�б�(��ѯ��Ʒ�б��ʱ����)
	private List<Order> OrderList;

	private Map<String, List<Product>> orderIdProductListMap;

	public OrderExecution() {
	}

	// ʧ�ܵĹ�����
	public OrderExecution(OrderStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// �ɹ��Ĺ�����
	public OrderExecution(OrderStateEnum stateEnum, Order Order) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.Order = Order;
	}

	// �ɹ��Ĺ�����
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
