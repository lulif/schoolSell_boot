package com.gdxx.dto;

import java.util.List;

import com.gdxx.entity.Shop;
import com.gdxx.enums.ShopStateEnum;
/*
 * ����Dto��
 */
public class ShopExecution {
	// �Y��״̬
	private int state;
	// ״̬��ʶ
	private String stateInfo;
	// ��������
	private int count;
	// ������shop(��ɾ�ĵ���)
	private Shop shop;
	// ������ѯ����
	private List<Shop> shopList;

	public ShopExecution() {

	}

	//����ʧ�ܵ�ʱ�򷵻�
	public ShopExecution(ShopStateEnum stateEnum) {
		super();
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();	
	}
	//�����ɹ���ʱ�򷵻�
	public ShopExecution(ShopStateEnum stateEnum,Shop shop) {
		super();
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();	
		this.shop=shop;
	}
	//���������ɹ���ʱ�򷵻�
	public ShopExecution(ShopStateEnum stateEnum,List<Shop> shopList) {
		super();
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();	
		this.shopList=shopList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

}
