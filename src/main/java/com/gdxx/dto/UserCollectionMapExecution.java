package com.gdxx.dto;

import java.util.List;

import com.gdxx.entity.UserCollectionMap;
import com.gdxx.enums.UserCollectionMapStateEnum;

public class UserCollectionMapExecution {
	// ���״̬
		private int state;

		// ״̬��ʶ
		private String stateInfo;

		// ��Ȩ��
		private Integer count;

		// ������UserCollectionMap
		private UserCollectionMap userCollectionMap;

		// ��Ȩ�б���ѯר�ã�
		private List<UserCollectionMap> userCollectionMapList;

		public UserCollectionMapExecution() {
		}

		// ʧ�ܵĹ�����
		public UserCollectionMapExecution(UserCollectionMapStateEnum stateEnum) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
		}

		// �ɹ��Ĺ�����
		public UserCollectionMapExecution(UserCollectionMapStateEnum stateEnum,
				UserCollectionMap userCollectionMap) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.userCollectionMap = userCollectionMap;
		}

		// �ɹ��Ĺ�����
		public UserCollectionMapExecution(UserCollectionMapStateEnum stateEnum,
				List<UserCollectionMap> userCollectionMapList) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.userCollectionMapList = userCollectionMapList;
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

		public UserCollectionMap getUserCollectionMap() {
			return userCollectionMap;
		}

		public void setUserCollectionMap(UserCollectionMap userCollectionMap) {
			this.userCollectionMap = userCollectionMap;
		}

		public List<UserCollectionMap> getUserCollectionMapList() {
			return userCollectionMapList;
		}

		public void setUserCollectionMapList(List<UserCollectionMap> userCollectionMapList) {
			this.userCollectionMapList = userCollectionMapList;
		}
}
