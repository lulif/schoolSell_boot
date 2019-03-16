package com.gdxx.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.gdxx.entity.LocalAuth;

public interface LocalAuthDao {
    //可以用来检验登陆的用户名和密码是否正确
	LocalAuth queryLocalByUserNameAndPwd(@Param("userName") String userName, @Param("password") String password);
    //綁定的時候查是否已經有此用戶了最多綁定一個
	LocalAuth queryLocalByUserId(@Param("userId") long userId);

	int insertLocalAuth(LocalAuth localAuth);

	int updateLocalAuth(@Param("userId") Long userId, @Param("userName") String userName,
			@Param("password") String password, @Param("newPassword") String newPassword,
			@Param("lastEditTime") Date lastEditTime);
}
