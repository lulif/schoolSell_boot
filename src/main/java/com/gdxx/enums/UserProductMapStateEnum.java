package com.gdxx.enums;

public enum UserProductMapStateEnum {
	SUCCESS(1, "�����ɹ�"), INNER_ERROR(-1001, "����ʧ��"), NULL_USERPRODUCT_ID(-1002,
			"UserProductIdΪ��"), NULL_USERPRODUCT_INFO(-1003, "�����˿յ���Ϣ");

	private int state;

	private String stateInfo;

	private UserProductMapStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static UserProductMapStateEnum stateOf(int index) {
		for (UserProductMapStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
