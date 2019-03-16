package com.gdxx.entity;

import java.util.Date;

public class CustomerComment {
	private Long CustomerCommentId;
	private PersonInfo customer;
	private Product product;
	private Shop shop;
	private Integer commentPoint;
	private String commentContent;
	private Date createTime;
	private Integer userProductId;

	public Long getCustomerCommentId() {
		return CustomerCommentId;
	}

	public void setCustomerCommentId(Long customerCommentId) {
		CustomerCommentId = customerCommentId;
	}

	public PersonInfo getCustomer() {
		return customer;
	}

	public void setCustomer(PersonInfo customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Integer getCommentPoint() {
		return commentPoint;
	}

	public void setCommentPoint(Integer commentPoint) {
		this.commentPoint = commentPoint;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUserProductId() {
		return userProductId;
	}

	public void setUserProductId(Integer userProductId) {
		this.userProductId = userProductId;
	}

}
