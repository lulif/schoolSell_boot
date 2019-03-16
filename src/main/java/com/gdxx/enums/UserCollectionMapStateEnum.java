package com.gdxx.enums;

public enum UserCollectionMapStateEnum {
	SUCCESS(1, "�����ɹ�"), INNER_ERROR(-1001, "����ʧ��"), NULL_USERCOLLECTION_ID(-1002,
			"userCollectionIdΪ��"), NULL_USERCOLLECTION_INFO(-1003, "�����˿յ���Ϣ"), HAD_COLLECTION(-1004, "����Ʒ���ղ�");

	private int state;

	private String stateInfo;

	private UserCollectionMapStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static UserCollectionMapStateEnum stateOf(int index) {
		for (UserCollectionMapStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
