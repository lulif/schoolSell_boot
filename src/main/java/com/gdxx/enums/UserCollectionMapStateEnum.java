package com.gdxx.enums;

public enum UserCollectionMapStateEnum {
	SUCCESS(1, "操作成功"), INNER_ERROR(-1001, "操作失败"), NULL_USERCOLLECTION_ID(-1002,
			"userCollectionId为空"), NULL_USERCOLLECTION_INFO(-1003, "传入了空的信息"), HAD_COLLECTION(-1004, "此商品已收藏");

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
