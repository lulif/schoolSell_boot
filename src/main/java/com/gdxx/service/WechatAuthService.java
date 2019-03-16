package com.gdxx.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.gdxx.dto.WechatAuthExecution;
import com.gdxx.entity.WechatAuth;

public interface WechatAuthService {
	WechatAuth getWechatAuthByOpenId(String openId);
	
	WechatAuthExecution register(WechatAuth wechatAuth,
			CommonsMultipartFile profileImg) throws RuntimeException;
}
