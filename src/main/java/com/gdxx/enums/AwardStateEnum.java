package com.gdxx.enums;


public enum AwardStateEnum {
	OFFLINE(-1, "�Ƿ���Ʒ"), SUCCESS(0, "�����ɹ�"), INNER_ERROR(-1001, "����ʧ��"), EMPTY(
			-1002, "��Ʒ��ϢΪ��");

	private int state;

	private String stateInfo;

	private AwardStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static AwardStateEnum stateOf(int index) {
		for (AwardStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
