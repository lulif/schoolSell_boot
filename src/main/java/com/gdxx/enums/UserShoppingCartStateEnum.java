package com.gdxx.enums;

public enum UserShoppingCartStateEnum {
	SUCCESS(1, "操作成功"), INNER_ERROR(-1001, "操作失败"), NULL_ID(-1002,
			"Id为空"), NULL_INFO(-1003, "传入了空的信息"), HAD_ADD(-1004, "此商品已加入购物车");

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
