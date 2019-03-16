package com.gdxx.service;

import com.gdxx.dto.LocalAuthExecution;
import com.gdxx.entity.LocalAuth;

public interface LocalAuthService {
	LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password);

	LocalAuth getLocalAuthByUserId(long userId);

	LocalAuthExecution register(LocalAuth localAuth) throws RuntimeException;

	LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws RuntimeException;

	LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword);
}
