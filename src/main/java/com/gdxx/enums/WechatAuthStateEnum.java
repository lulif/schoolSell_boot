package com.gdxx.enums;

public enum WechatAuthStateEnum {
	LOGINFAIL(-1, "openId��������"), SUCCESS(0, "�����ɹ�"), NULL_AUTH_INFO(-1006,
			"ע����ϢΪ��");

	private int state;

	private String stateInfo;

	private WechatAuthStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static WechatAuthStateEnum stateOf(int index) {
		for (WechatAuthStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
