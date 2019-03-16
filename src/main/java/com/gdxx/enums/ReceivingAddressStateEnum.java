package com.gdxx.enums;

public enum ReceivingAddressStateEnum {
	SUCCESS(1, "操作成功"), INNER_ERROR(-1001, "操作失败"), NULL_ID(-1002, "Id为空"), NULL_INFO(-1003, "传入了空的信息");

	private int state;

	private String stateInfo;

	private ReceivingAddressStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static ReceivingAddressStateEnum stateOf(int index) {
		for (ReceivingAddressStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
