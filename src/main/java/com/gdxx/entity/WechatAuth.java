package com.gdxx.entity;

import java.util.Date;
/*
 * 微信账号
 */
public class WechatAuth {
	// 微信ID
	private Long wechatAuthId;
	// 和用户绑定唯一标识
	private String openId;
	// 创建时间
	private Date createTime;
	// 用户实体
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
