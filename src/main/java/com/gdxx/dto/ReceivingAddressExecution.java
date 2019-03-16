package com.gdxx.dto;

import java.util.List;

import com.gdxx.entity.ReceivingAddress;
import com.gdxx.enums.ReceivingAddressStateEnum;

public class ReceivingAddressExecution {
	// 结果状态
		private int state;

		// 状态标识
		private String stateInfo;

		// 授权数
		private Integer count;

		private ReceivingAddress ReceivingAddress;

		// 授权列表（查询专用）
		private List<ReceivingAddress> ReceivingAddressList;

		public ReceivingAddressExecution() {
		}

		// 失败的构造器
		public ReceivingAddressExecution(ReceivingAddressStateEnum stateEnum) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
		}

		// 成功的构造器
		public ReceivingAddressExecution(ReceivingAddressStateEnum stateEnum, ReceivingAddress ReceivingAddress) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.ReceivingAddress = ReceivingAddress;
		}

		// 成功的构造器
		public ReceivingAddressExecution(ReceivingAddressStateEnum stateEnum, List<ReceivingAddress> ReceivingAddressList) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.ReceivingAddressList = ReceivingAddressList;
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

		public ReceivingAddress getReceivingAddress() {
			return ReceivingAddress;
		}

		public void setReceivingAddress(ReceivingAddress ReceivingAddress) {
			this.ReceivingAddress = ReceivingAddress;
		}

		public List<ReceivingAddress> getReceivingAddressList() {
			return ReceivingAddressList;
		}

		public void setReceivingAddressList(List<ReceivingAddress> ReceivingAddressList) {
			this.ReceivingAddressList = ReceivingAddressList;
		}
}
