package com.gdxx.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.gdxx.entity.LocalAuth;

public interface LocalAuthDao {
    //�������������½���û����������Ƿ���ȷ
	LocalAuth queryLocalByUserNameAndPwd(@Param("userName") String userName, @Param("password") String password);
    //�����ĕr����Ƿ��ѽ��д��Ñ�����ཉ��һ��
	LocalAuth queryLocalByUserId(@Param("userId") long userId);

	int insertLocalAuth(LocalAuth localAuth);

	int updateLocalAuth(@Param("userId") Long userId, @Param("userName") String userName,
			@Param("password") String password, @Param("newPassword") String newPassword,
			@Param("lastEditTime") Date lastEditTime);
}
