package com.gdxx.entity;

import java.util.Date;
/*
 * �����˺�:��½ʱʹ�õ�
 */
public class LocalAuth {
	//�����˺�ID
	private Long localAuthId;
	//�û���
	private String userName;
	//����
	private String password;
	//����ʱ��
	private Date createTime;
	//��һ���޸�ʱ��
	private Date lastEditTime;
	//�û�ʵ��
	private PersonInfo personInfo;

	

	public Long getLocalAuthId() {
		return localAuthId;
	}

	public void setLocalAuthId(Long localAuthId) {
		this.localAuthId = localAuthId;
	}

	public Date getCreateTime() {
		return createTime;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

}
