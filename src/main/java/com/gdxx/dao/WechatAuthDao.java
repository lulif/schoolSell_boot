package com.gdxx.dao;

import com.gdxx.entity.WechatAuth;

public interface WechatAuthDao {
	WechatAuth queryWechatInfoByOpenId(String openId);

	int insertWechatAuth(WechatAuth wechatAuth);
}
