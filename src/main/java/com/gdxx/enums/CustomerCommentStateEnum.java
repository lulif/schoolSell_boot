package com.gdxx.enums;

public enum CustomerCommentStateEnum {
	 SUCCESS(0, "操作成功"), INNER_ERROR(-1001, "操作失败"), EMPTY(
			-1002, "所需信息为空");

	private int state;

	private String stateInfo;

	private CustomerCommentStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static CustomerCommentStateEnum stateOf(int index) {
		for (CustomerCommentStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
