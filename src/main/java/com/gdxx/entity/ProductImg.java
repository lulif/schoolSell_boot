package com.gdxx.entity;

import java.util.Date;
/*
 * 商品图片
 */
public class ProductImg {
	//商品图片ID
	private Long productImgId;
	//图片地址
	private String imgAddr;
	//图片描述
	private String imgDesc;
	//权重
	private Integer priority;
	//创建时间
	private Date createTime;
	//哪个商品的详情图片
	private Long productId;


	public Long getProductImgId() {
		return productImgId;
	}

	public void setProductImgId(Long productImgId) {
		this.productImgId = productImgId;
	}

	public String getImgAddr() {
		return imgAddr;
	}

	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}

	public String getImgDesc() {
		return imgDesc;
	}

	public void setImgDesc(String imgDesc) {
		this.imgDesc = imgDesc;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "ProductImg [productImgId=" + productImgId + ", imgAddr=" + imgAddr + ", imgDesc=" + imgDesc
				+ ", priority=" + priority + ", createTime=" + createTime + ", productId=" + productId + "]";
	}

}
