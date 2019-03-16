package com.gdxx.dto;

import java.util.List;

import com.gdxx.entity.UserCollectionMap;
import com.gdxx.enums.UserCollectionMapStateEnum;

public class UserCollectionMapExecution {
	// 结果状态
		private int state;

		// 状态标识
		private String stateInfo;

		// 授权数
		private Integer count;

		// 操作的UserCollectionMap
		private UserCollectionMap userCollectionMap;

		// 授权列表（查询专用）
		private List<UserCollectionMap> userCollectionMapList;

		public UserCollectionMapExecution() {
		}

		// 失败的构造器
		public UserCollectionMapExecution(UserCollectionMapStateEnum stateEnum) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
		}

		// 成功的构造器
		public UserCollectionMapExecution(UserCollectionMapStateEnum stateEnum,
				UserCollectionMap userCollectionMap) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.userCollectionMap = userCollectionMap;
		}

		// 成功的构造器
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
