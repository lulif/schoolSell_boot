package com.gdxx.service;

import com.gdxx.dto.UserAwardMapExecution;
import com.gdxx.entity.UserAwardMap;

public interface UserAwardMapService  {
	UserAwardMapExecution listUserAwardMap(UserAwardMap userAwardCondition,
			Integer pageIndex, Integer pageSize);
	
	
	UserAwardMap getUserAwardMapById(long userAwardMapId);
	
	UserAwardMapExecution addUserAwardMap(UserAwardMap userAwardMap)
			throws RuntimeException;
	
	UserAwardMapExecution modifyUserAwardMap(UserAwardMap userAwardMap)
			throws RuntimeException;

}
