package com.gdxx.service;

import com.gdxx.dto.UserCollectionMapExecution;
import com.gdxx.entity.UserCollectionMap;

public interface UserCollectionMapService {
	UserCollectionMapExecution getUserCollectionMapList(UserCollectionMap userCollectionCondition, Integer pageIndex,
			Integer pageSize);

	UserCollectionMapExecution addUserCollectionMap(UserCollectionMap userCollectionMap)throws RuntimeException;

	UserCollectionMapExecution removeUserCollectionMap(Long userCollectionId)throws RuntimeException;

}
