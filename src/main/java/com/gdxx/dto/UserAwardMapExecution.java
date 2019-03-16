package com.gdxx.dto;

import java.util.List;

import com.gdxx.entity.UserAwardMap;
import com.gdxx.enums.UserAwardMapStateEnum;

public class UserAwardMapExecution {
	// ���״̬
	private int state;

	// ״̬��ʶ
	private String stateInfo;

	// ��Ȩ��
	private Integer count;

	// ������UserAwardMap
	private UserAwardMap userAwardMap;

	// ��Ȩ�б���ѯר�ã�
	private List<UserAwardMap> userAwardMapList;

	public UserAwardMapExecution() {
	}

	// ʧ�ܵĹ�����
	public UserAwardMapExecution(UserAwardMapStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// �ɹ��Ĺ�����
	public UserAwardMapExecution(UserAwardMapStateEnum stateEnum,
			UserAwardMap userAwardMap) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.userAwardMap = userAwardMap;
	}

	// �ɹ��Ĺ�����
	public UserAwardMapExecution(UserAwardMapStateEnum stateEnum,
			List<UserAwardMap> userAwardMapList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.userAwardMapList = userAwardMapList;
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

	public UserAwardMap getUserAwardMap() {
		return userAwardMap;
	}

	public void setUserAwardMap(UserAwardMap userAwardMap) {
		this.userAwardMap = userAwardMap;
	}

	public List<UserAwardMap> getUserAwardMapList() {
		return userAwardMapList;
	}

	public void setUserAwardMapList(List<UserAwardMap> userAwardMapList) {
		this.userAwardMapList = userAwardMapList;
	}

}
