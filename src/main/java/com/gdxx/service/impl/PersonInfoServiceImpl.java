package com.gdxx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdxx.dao.PersonInfoDao;
import com.gdxx.dto.PersonInfoExecution;
import com.gdxx.entity.PersonInfo;
import com.gdxx.enums.PersonInfoStateEnum;
import com.gdxx.service.PersonInfoService;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {
	@Autowired
	private PersonInfoDao personInfoDao;

	@Override
	public PersonInfo getPersonInfoById(Long userId) {
		return personInfoDao.queryPersonInfoById(userId);
	}

	@Override
	public PersonInfoExecution addPersonInfo(PersonInfo personInfo) {
		if (personInfo == null) {
			return new PersonInfoExecution(PersonInfoStateEnum.EMPTY);
		} else {
			try {
				int effNum = personInfoDao.insertPersonInfo(personInfo);
				if (effNum <= 0) {
					return new PersonInfoExecution(PersonInfoStateEnum.INNER_ERROR);
				} else {
					personInfo = personInfoDao.queryPersonInfoById(personInfo.getUserId());
					return new PersonInfoExecution(PersonInfoStateEnum.SUCCESS, personInfo);
				}
			} catch (Exception e) {
				throw new RuntimeException("addPersonInfo error: " + e.toString());
			}
		}
	}

}
