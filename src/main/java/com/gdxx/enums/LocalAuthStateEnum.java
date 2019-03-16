package com.gdxx.enums;

public enum LocalAuthStateEnum {
	LOGINFAIL(-1, "������ʺ���������"), SUCCESS(0, "�����ɹ�"), NULL_AUTH_INFO(-1006, "ע����ϢΪ��"), ONLY_ONE_ACCOUNT(-1007,
			"���ֻ�ܰ�һ�������ʺ�");

	private int state;

	private String stateInfo;

	private LocalAuthStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static LocalAuthStateEnum stateOf(int index) {
		for (LocalAuthStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
