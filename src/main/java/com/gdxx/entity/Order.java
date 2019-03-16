package com.gdxx.entity;

import java.util.Date;
import java.util.List;

public class Order {
	private String orderId;
	private ReceivingAddress receivingAddress;
	private Long userId;
	/*
	 * 0表示无效(被删除的订单) 1表示待付款(又取消了订单) 2表示待发货(已支付) 3待收货(卖家已发货)
	 */
	private Integer orderStatus;
	private Date createTime;
	private Date updateTime;
	private Date expireTime;
	private Double moneyAccount;
	private List<OrderDetail> orderDetailList;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public ReceivingAddress getReceivingAddress() {
		return receivingAddress;
	}

	public void setReceivingAddress(ReceivingAddress receivingAddress) {
		this.receivingAddress = receivingAddress;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Double getMoneyAccount() {
		return moneyAccount;
	}

	public void setMoneyAccount(Double moneyAccount) {
		this.moneyAccount = moneyAccount;
	}

	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

}
