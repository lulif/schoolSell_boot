package com.gdxx.enums;

public enum ShopStateEnum {
	CHECK(0, "审核中"), OFFLINE(-1, "非法店铺"), SUCCESS(1, "操作成功"), PASS(2, "通过认证"), INNER_ERROR(-1001,
			"内部错误"), NULL_SHOPID(-1002, "ShopID为空"),NULL_SHOP(-1003,"Shop信息为空");

	private int state;
	private String stateInfo;

	// 不希望外界改变Enum值 所以构造器私有化
	private ShopStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	// 传入state得到响应enum的值
	public static ShopStateEnum stateOf(int state) {
		for (ShopStateEnum stateEnum : values()) {
			if (stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

}
