package com.gdxx.dto;

import java.util.List;

import com.gdxx.entity.CustomerComment;
import com.gdxx.enums.CustomerCommentStateEnum;

public class CustomerCommentExecution {
	// ���״̬
		private int state;

		// ״̬��ʶ
		private String stateInfo;

		// ��Ȩ��
		private Integer count;

		// ������CustomerComment
		private CustomerComment customerComment;

		// ��Ȩ�б���ѯר�ã�
		private List<CustomerComment> customerCommentList;

		public CustomerCommentExecution() {
		}

		// ʧ�ܵĹ�����
		public CustomerCommentExecution(CustomerCommentStateEnum stateEnum) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
		}

		// �ɹ��Ĺ�����
		public CustomerCommentExecution(CustomerCommentStateEnum stateEnum,
				CustomerComment CustomerComment) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.customerComment = CustomerComment;
		}

		// �ɹ��Ĺ�����
		public CustomerCommentExecution(CustomerCommentStateEnum stateEnum,
				List<CustomerComment> CustomerCommentList) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.customerCommentList = CustomerCommentList;
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

		public CustomerComment getCustomerComment() {
			return customerComment;
		}

		public void setCustomerComment(CustomerComment CustomerComment) {
			this.customerComment = CustomerComment;
		}

		public List<CustomerComment> getCustomerCommentList() {
			return customerCommentList;
		}

		public void setCustomerCommentList(List<CustomerComment> CustomerCommentList) {
			this.customerCommentList = CustomerCommentList;
		}
}
