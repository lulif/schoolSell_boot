package com.gdxx.entity;

import java.util.Date;

/*
 * 商品每天的消费
 */
public class ProductSellDaily {
	private Integer productSellDailyId;
	private Date createTime;
	private Integer total;
	private Product product;
	private Shop shop;

	public Integer getProductSellDailyId() {
		return productSellDailyId;
	}

	public void setProductSellDailyId(Integer productSellDailyId) {
		this.productSellDailyId = productSellDailyId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
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

}
