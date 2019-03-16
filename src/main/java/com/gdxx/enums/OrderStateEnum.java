package com.gdxx.enums;

public enum OrderStateEnum {
	FAIL(-1, "操作失败"), SUCCESS(0, "操作成功"), INNER_ERROR(-1001, "内部错误"), EMPTY(-1002, "订单信息为空"), ADDRESS_INVALID(-1003,
			"地址无效"), PRODUCT_NULL(-1004, "未找到相关商品"), CHENK_MONEY_ERROR(-1005,
					"金额校对有误"), INFO_NULL(-1006, "所需信息为空"), NULL_ORDER(-1007, "未查询到相关订单");

	private int state;

	private String stateInfo;

	private OrderStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static OrderStateEnum stateOf(int index) {
		for (OrderStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
