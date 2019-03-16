package com.gdxx.service;

import com.gdxx.dto.PersonInfoExecution;
import com.gdxx.entity.PersonInfo;

public interface PersonInfoService {

	PersonInfo getPersonInfoById(Long userId);

	PersonInfoExecution addPersonInfo(PersonInfo personInfo);
}
