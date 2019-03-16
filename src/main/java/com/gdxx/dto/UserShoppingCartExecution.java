package com.gdxx.dto;

import java.util.List;

import com.gdxx.entity.UserShoppingCart;
import com.gdxx.enums.UserShoppingCartStateEnum;

public class UserShoppingCartExecution {
	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 授权数
	private Integer count;

	private UserShoppingCart userShoppingCart;

	// 授权列表（查询专用）
	private List<UserShoppingCart> UserShoppingCartList;

	public UserShoppingCartExecution() {
	}

	// 失败的构造器
	public UserShoppingCartExecution(UserShoppingCartStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 成功的构造器
	public UserShoppingCartExecution(UserShoppingCartStateEnum stateEnum, UserShoppingCart UserShoppingCart) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.userShoppingCart = UserShoppingCart;
	}

	// 成功的构造器
	public UserShoppingCartExecution(UserShoppingCartStateEnum stateEnum, List<UserShoppingCart> UserShoppingCartList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.UserShoppingCartList = UserShoppingCartList;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public UserShoppingCart getUserShoppingCart() {
		return userShoppingCart;
	}

	public void setUserShoppingCart(UserShoppingCart UserShoppingCart) {
		this.userShoppingCart = UserShoppingCart;
	}

	public List<UserShoppingCart> getUserShoppingCartList() {
		return UserShoppingCartList;
	}

	public void setUserShoppingCartList(List<UserShoppingCart> UserShoppingCartList) {
		this.UserShoppingCartList = UserShoppingCartList;
	}
}
