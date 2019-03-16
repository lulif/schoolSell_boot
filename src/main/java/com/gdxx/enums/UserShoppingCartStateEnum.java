package com.gdxx.enums;

public enum UserShoppingCartStateEnum {
	SUCCESS(1, "�����ɹ�"), INNER_ERROR(-1001, "����ʧ��"), NULL_ID(-1002,
			"IdΪ��"), NULL_INFO(-1003, "�����˿յ���Ϣ"), HAD_ADD(-1004, "����Ʒ�Ѽ��빺�ﳵ");

	private int state;

	private String stateInfo;

	private UserShoppingCartStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static UserShoppingCartStateEnum stateOf(int index) {
		for (UserShoppingCartStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
