package com.gdxx.enums;

public enum OrderStateEnum {
	FAIL(-1, "����ʧ��"), SUCCESS(0, "�����ɹ�"), INNER_ERROR(-1001, "�ڲ�����"), EMPTY(-1002, "������ϢΪ��"), ADDRESS_INVALID(-1003,
			"��ַ��Ч"), PRODUCT_NULL(-1004, "δ�ҵ������Ʒ"), CHENK_MONEY_ERROR(-1005,
					"���У������"), INFO_NULL(-1006, "������ϢΪ��"), NULL_ORDER(-1007, "δ��ѯ����ض���");

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
