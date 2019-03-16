package com.gdxx.dto;

import java.util.List;

import com.gdxx.entity.UserShopMap;
import com.gdxx.enums.UserShopMapStateEnum;

public class UserShopMapExecution {
	// ���״̬
	private int state;

	// ״̬��ʶ
	private String stateInfo;

	// ��Ȩ��
	private Integer count;

	// ������UserShopMap
	private UserShopMap userShopMap;

	// ��Ȩ�б���ѯר�ã�
	private List<UserShopMap> userShopMapList;

	public UserShopMapExecution() {
	}

	// ʧ�ܵĹ�����
	public UserShopMapExecution(UserShopMapStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// �ɹ��Ĺ�����
	public UserShopMapExecution(UserShopMapStateEnum stateEnum,
			UserShopMap userShopMap) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.userShopMap = userShopMap;
	}

	// �ɹ��Ĺ�����
	public UserShopMapExecution(UserShopMapStateEnum stateEnum,
			List<UserShopMap> userShopMapList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.userShopMapList = userShopMapList;
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

	public UserShopMap getUserShopMap() {
		return userShopMap;
	}

	public void setUserShopMap(UserShopMap userShopMap) {
		this.userShopMap = userShopMap;
	}

	public List<UserShopMap> getUserShopMapList() {
		return userShopMapList;
	}

	public void setUserShopMapList(List<UserShopMap> userShopMapList) {
		this.userShopMapList = userShopMapList;
	}

}
