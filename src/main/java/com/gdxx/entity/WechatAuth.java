package com.gdxx.entity;

import java.util.Date;
/*
 * ΢���˺�
 */
public class WechatAuth {
	// ΢��ID
	private Long wechatAuthId;
	// ���û���Ψһ��ʶ
	private String openId;
	// ����ʱ��
	private Date createTime;
	// �û�ʵ��
	private PersonInfo personInfo;


	public Long getWeChatAuthId() {
		return wechatAuthId;
	}

	public void setWeChatAuthId(Long weChatAuthId) {
		this.wechatAuthId = weChatAuthId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

}
