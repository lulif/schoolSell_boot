package com.gdxx.entity;

import java.util.Date;

public class UserCollectionMap {
	private Long userCollectionId;
	private PersonInfo user;
	private Shop shop;
	private Product product;
	private Date createTime;

	public Long getUserCollectionId() {
		return userCollectionId;
	}

	public void setUserCollectionId(Long userCollectionId) {
		this.userCollectionId = userCollectionId;
	}

	public PersonInfo getUser() {
		return user;
	}

	public void setUser(PersonInfo user) {
		this.user = user;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


}
