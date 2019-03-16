package com.gdxx.dto;

import java.util.List;

import com.gdxx.entity.ProductCategory;
import com.gdxx.enums.ProductCategoryStateEnum;

public class ProductCategoryExecution {
	// ½Y¹û×´Ì¬
	private int state;
	// ×´Ì¬±êÊ¶
	private String stateInfo;

	private List<ProductCategory> productCategoryList;

	public ProductCategoryExecution() {

	}

	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, List<ProductCategory> productCategoryList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productCategoryList = productCategoryList;
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

	public List<ProductCategory> getProductCategoryList() {
		return productCategoryList;
	}

	public void setProductCategoryList(List<ProductCategory> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}

}
