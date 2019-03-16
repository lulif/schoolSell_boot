package com.gdxx.enums;

public enum ShopStateEnum {
	CHECK(0, "�����"), OFFLINE(-1, "�Ƿ�����"), SUCCESS(1, "�����ɹ�"), PASS(2, "ͨ����֤"), INNER_ERROR(-1001,
			"�ڲ�����"), NULL_SHOPID(-1002, "ShopIDΪ��"),NULL_SHOP(-1003,"Shop��ϢΪ��");

	private int state;
	private String stateInfo;

	// ��ϣ�����ı�Enumֵ ���Թ�����˽�л�
	private ShopStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	// ����state�õ���Ӧenum��ֵ
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
