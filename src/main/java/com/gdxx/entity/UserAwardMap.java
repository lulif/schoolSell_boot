package com.gdxx.entity;

import java.util.Date;
/*
 * �û��һ���Ʒӳ��
 */
public class UserAwardMap {

	private Long userAwardId;
	private Date createTime;
	private Integer usedStatus;
	//�һ���Ʒ����Ļ���
	private Integer point;
	//�˿���Ϣ
	private PersonInfo user;
	//��Ʒ��Ϣ
	private Award award;
	//�̵���Ϣ
	private Shop shop;
	//����Ա
	private PersonInfo operator;

	public Long getUserAwardId() {
		return userAwardId;
	}

	public void setUserAwardId(Long userAwardId) {
		this.userAwardId = userAwardId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUsedStatus() {
		return usedStatus;
	}

	public void setUsedStatus(Integer usedStatus) {
		this.usedStatus = usedStatus;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public PersonInfo getUser() {
		return user;
	}

	public void setUser(PersonInfo user) {
		this.user = user;
	}

	public Award getAward() {
		return award;
	}

	public void setAward(Award award) {
		this.award = award;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public PersonInfo getOperator() {
		return operator;
	}

	public void setOperator(PersonInfo operator) {
		this.operator = operator;
	}
	

}
