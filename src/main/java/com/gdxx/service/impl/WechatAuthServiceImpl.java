package com.gdxx.service.impl;

import java.util.Date;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.gdxx.dao.PersonInfoDao;
import com.gdxx.dao.WechatAuthDao;
import com.gdxx.dto.WechatAuthExecution;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.WechatAuth;
import com.gdxx.enums.WechatAuthStateEnum;
import com.gdxx.service.WechatAuthService;

import ch.qos.logback.classic.Logger;
@Service
public class WechatAuthServiceImpl implements WechatAuthService {
	@Autowired
	private WechatAuthDao wechatAuthDao;
	@Autowired
	private PersonInfoDao personInfoDao;
	
	private static Logger log = (Logger) LoggerFactory.getLogger(WechatAuthServiceImpl.class);


	@Override
	public WechatAuth getWechatAuthByOpenId(String openId) {
		return wechatAuthDao.queryWechatInfoByOpenId(openId);
	}

	@Override
	@Transactional
	public WechatAuthExecution register(WechatAuth wechatAuth, CommonsMultipartFile profileImg)
			throws RuntimeException {
		if (wechatAuth == null || wechatAuth.getOpenId() == null) {
			return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
		}
		try {
			wechatAuth.setCreateTime(new Date());
			//��һ��ʹ��ƽ̨��ͨ��΢�ŵ�½
			if (wechatAuth.getPersonInfo() != null && wechatAuth.getPersonInfo().getUserId() == null) {
//				if (profileImg != null) {
//					try {
//						addProfileImg(wechatAuth, profileImg);
//					} catch (Exception e) {
//						log.debug("addUserProfileImg error:" + e.toString());
//						throw new RuntimeException("addUserProfileImg error: " + e.getMessage());
//					}
//				}
				try {
					wechatAuth.getPersonInfo().setCreateTime(new Date());
					wechatAuth.getPersonInfo().setLastEditTime(new Date());
					wechatAuth.getPersonInfo().setEnableStatus(1);
					PersonInfo personInfo = wechatAuth.getPersonInfo();
					int effectedNum = personInfoDao.insertPersonInfo(personInfo);
					//����֮�����userId�ˣ��ٴ����õ�wechatAuth��
					wechatAuth.setPersonInfo(personInfo);
					if (effectedNum <= 0) {
						throw new RuntimeException("����û���Ϣʧ��");
					}
				} catch (Exception e) {
					log.debug("insertPersonInfo error:" + e.toString());
					throw new RuntimeException("insertPersonInfo error: " + e.getMessage());
				}
			}
			//userId��Ϊ��
			int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
			if (effectedNum <= 0) {
				throw new RuntimeException("�ʺŴ���ʧ��");
			} else {
				return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS, wechatAuth);
			}
		} catch (Exception e) {
			log.debug("insertWechatAuth error:" + e.toString());
			throw new RuntimeException("insertWechatAuth error: " + e.getMessage());
		}
	}

//	private void addProfileImg(WechatAuth wechatAuth, CommonsMultipartFile profileImg) {
//		String dest = PathUtil.getImageBasePath();
//		String profileImgAddr = ImageUtil.generateThumbnail(profileImg, dest, false);
//		wechatAuth.getPersonInfo().setProfileImg(profileImgAddr);
//	}
}
