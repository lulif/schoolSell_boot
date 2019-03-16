package com.gdxx.dto;

import java.util.List;

import com.gdxx.entity.ReceivingAddress;
import com.gdxx.enums.ReceivingAddressStateEnum;

public class ReceivingAddressExecution {
	// ���״̬
		private int state;

		// ״̬��ʶ
		private String stateInfo;

		// ��Ȩ��
		private Integer count;

		private ReceivingAddress ReceivingAddress;

		// ��Ȩ�б���ѯר�ã�
		private List<ReceivingAddress> ReceivingAddressList;

		public ReceivingAddressExecution() {
		}

		// ʧ�ܵĹ�����
		public ReceivingAddressExecution(ReceivingAddressStateEnum stateEnum) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
		}

		// �ɹ��Ĺ�����
		public ReceivingAddressExecution(ReceivingAddressStateEnum stateEnum, ReceivingAddress ReceivingAddress) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.ReceivingAddress = ReceivingAddress;
		}

		// �ɹ��Ĺ�����
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
