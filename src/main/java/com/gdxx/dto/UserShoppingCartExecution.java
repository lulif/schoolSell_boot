package com.gdxx.dto;

import java.util.List;

import com.gdxx.entity.UserShoppingCart;
import com.gdxx.enums.UserShoppingCartStateEnum;

public class UserShoppingCartExecution {
	// ���״̬
	private int state;

	// ״̬��ʶ
	private String stateInfo;

	// ��Ȩ��
	private Integer count;

	private UserShoppingCart userShoppingCart;

	// ��Ȩ�б���ѯר�ã�
	private List<UserShoppingCart> UserShoppingCartList;

	public UserShoppingCartExecution() {
	}

	// ʧ�ܵĹ�����
	public UserShoppingCartExecution(UserShoppingCartStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// �ɹ��Ĺ�����
	public UserShoppingCartExecution(UserShoppingCartStateEnum stateEnum, UserShoppingCart UserShoppingCart) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.userShoppingCart = UserShoppingCart;
	}

	// �ɹ��Ĺ�����
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
