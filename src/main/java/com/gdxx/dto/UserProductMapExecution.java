package com.gdxx.dto;

import java.util.List;

import com.gdxx.entity.UserProductMap;
import com.gdxx.enums.UserProductMapStateEnum;



public class UserProductMapExecution {
	// ���״̬
	private int state;

	// ״̬��ʶ
	private String stateInfo;

	// ��Ȩ��
	private Integer count;

	// ������shopAuthMap
	private UserProductMap userProductMap;

	// ��Ȩ�б���ѯר�ã�
	private List<UserProductMap> userProductMapList;

	public UserProductMapExecution() {
	}

	// ʧ�ܵĹ�����
	public UserProductMapExecution(UserProductMapStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// �ɹ��Ĺ�����
	public UserProductMapExecution(UserProductMapStateEnum stateEnum,
			UserProductMap userProductMap) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.userProductMap = userProductMap;
	}

	// �ɹ��Ĺ�����
	public UserProductMapExecution(UserProductMapStateEnum stateEnum,
			List<UserProductMap> userProductMapList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.userProductMapList = userProductMapList;
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

	public UserProductMap getUserProductMap() {
		return userProductMap;
	}

	public void setUserProductMap(UserProductMap userProductMap) {
		this.userProductMap = userProductMap;
	}

	public List<UserProductMap> getUserProductMapList() {
		return userProductMapList;
	}

	public void setUserProductMapList(List<UserProductMap> userProductMapList) {
		this.userProductMapList = userProductMapList;
	}

}
