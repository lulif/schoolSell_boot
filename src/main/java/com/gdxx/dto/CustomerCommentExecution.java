package com.gdxx.dto;

import java.util.List;

import com.gdxx.entity.CustomerComment;
import com.gdxx.enums.CustomerCommentStateEnum;

public class CustomerCommentExecution {
	// 结果状态
		private int state;

		// 状态标识
		private String stateInfo;

		// 授权数
		private Integer count;

		// 操作的CustomerComment
		private CustomerComment customerComment;

		// 授权列表（查询专用）
		private List<CustomerComment> customerCommentList;

		public CustomerCommentExecution() {
		}

		// 失败的构造器
		public CustomerCommentExecution(CustomerCommentStateEnum stateEnum) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
		}

		// 成功的构造器
		public CustomerCommentExecution(CustomerCommentStateEnum stateEnum,
				CustomerComment CustomerComment) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.customerComment = CustomerComment;
		}

		// 成功的构造器
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
