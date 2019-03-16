package com.gdxx.dto;

import java.util.List;

import com.gdxx.entity.Shop;
import com.gdxx.enums.ShopStateEnum;
/*
 * 店铺Dto类
 */
public class ShopExecution {
	// 結果状态
	private int state;
	// 状态标识
	private String stateInfo;
	// 店铺数量
	private int count;
	// 操作的shop(增删改店铺)
	private Shop shop;
	// 批量查询店铺
	private List<Shop> shopList;

	public ShopExecution() {

	}

	//操作失败的时候返回
	public ShopExecution(ShopStateEnum stateEnum) {
		super();
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();	
	}
	//操作成功的时候返回
	public ShopExecution(ShopStateEnum stateEnum,Shop shop) {
		super();
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();	
		this.shop=shop;
	}
	//批量操作成功的时候返回
	public ShopExecution(ShopStateEnum stateEnum,List<Shop> shopList) {
		super();
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();	
		this.shopList=shopList;
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

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

}
