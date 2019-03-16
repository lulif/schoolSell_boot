package com.gdxx.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.gdxx.dao.LocalAuthDao;
import com.gdxx.dao.PersonInfoDao;
import com.gdxx.dto.LocalAuthExecution;
import com.gdxx.entity.LocalAuth;
import com.gdxx.entity.PersonInfo;
import com.gdxx.enums.LocalAuthStateEnum;
import com.gdxx.service.LocalAuthService;
import com.gdxx.util.MD5;

@Service
public class LocalAuthServiceImpl implements LocalAuthService {
	@Autowired
	private LocalAuthDao localAuthDao;
	@Autowired
	private PersonInfoDao personInfoDao;

	@Override
	public LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password) {
		return localAuthDao.queryLocalByUserNameAndPwd(userName, password);
	}

	@Override
	public LocalAuth getLocalAuthByUserId(long userId) {
		return localAuthDao.queryLocalByUserId(userId);
	}

	@Override
	@Transactional
	public LocalAuthExecution register(LocalAuth localAuth) throws RuntimeException {

		if (localAuth == null || localAuth.getPassword() == null || localAuth.getUserName() == null) {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
		// 没有注册过
		localAuth.setCreateTime(new Date());
		localAuth.setLastEditTime(new Date());
		localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
		if (localAuth.getPersonInfo() != null && localAuth.getPersonInfo().getUserId() == null) {
			try {
				PersonInfo personInfo = localAuth.getPersonInfo();
				personInfo.setEnableStatus(1);
				personInfo.setCreateTime(new Date());
				personInfo.setLastEditTime(new Date());
				int effectedNum = personInfoDao.insertPersonInfo(personInfo);
				localAuth.setPersonInfo(personInfo);
				if (effectedNum <= 0) {
					throw new RuntimeException("添加用户信息失败");
				}
			} catch (Exception e) {
				throw new RuntimeException("insertPersonInfo error: " + e.getMessage());
			}
		}
		try {
			int effectedNum = localAuthDao.insertLocalAuth(localAuth);
			if (effectedNum <= 0) {
				throw new RuntimeException("帐号创建失败");
			} else {
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS, localAuth);
			}
		} catch (Exception e) {
			throw new RuntimeException("insertLocalAuth error: " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws RuntimeException {

		if (localAuth == null || localAuth.getPassword() == null || localAuth.getUserName() == null
				|| localAuth.getPersonInfo().getUserId() == null) {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
		// localAuth表中的userid是外键，userId可以查出东西来那肯定已经绑定过了
		LocalAuth tempAuth = localAuthDao.queryLocalByUserId(localAuth.getPersonInfo().getUserId());
		if (tempAuth != null) {
			return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
		}
		try {
			localAuth.setCreateTime(new Date());
			localAuth.setLastEditTime(new Date());
			localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
			int effectedNum = localAuthDao.insertLocalAuth(localAuth);
			if (effectedNum <= 0) {
				throw new RuntimeException("帐号绑定失败");
			} else {
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS, localAuth);
			}
		} catch (Exception e) {
			throw new RuntimeException("insertLocalAuth error: " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword) {

		if (userId != null && userName != null && password != null && newPassword != null
				&& !password.equals(newPassword)) {
			try {
				int effectedNum = localAuthDao.updateLocalAuth(userId, userName, MD5.getMd5(password),
						MD5.getMd5(newPassword), new Date());
				if (effectedNum <= 0) {
					throw new RuntimeException("更新密码失败");
				}
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
			} catch (Exception e) {
				throw new RuntimeException("更新密码失败:" + e.toString());
			}
		} else {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
	}

}
