package com.gdxx.enums;

public enum ReceivingAddressStateEnum {
	SUCCESS(1, "�����ɹ�"), INNER_ERROR(-1001, "����ʧ��"), NULL_ID(-1002, "IdΪ��"), NULL_INFO(-1003, "�����˿յ���Ϣ");

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
