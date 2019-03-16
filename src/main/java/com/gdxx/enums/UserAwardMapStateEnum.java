package com.gdxx.enums;

public enum UserAwardMapStateEnum {
	SUCCESS(1, "�����ɹ�"), INNER_ERROR(-1001, "����ʧ��"), NULL_USERAWARD_ID(-1002,
			"UserAwardIdΪ��"), NULL_USERAWARD_INFO(-1003, "�����˿յ���Ϣ");

	private int state;

	private String stateInfo;

	private UserAwardMapStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static UserAwardMapStateEnum stateOf(int index) {
		for (UserAwardMapStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
