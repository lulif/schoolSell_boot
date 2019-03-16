package com.gdxx.dao;

import com.gdxx.entity.PersonInfo;

public interface PersonInfoDao {
	PersonInfo queryPersonInfoById(long userId);

	int insertPersonInfo(PersonInfo personInfo);
}
