package com.gdxx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdxx.entity.UserCollectionMap;

public interface UserCollectionMapDao {
	List<UserCollectionMap> queryUserCollectionMapList(
			@Param("userCollectionCondition") UserCollectionMap userCollectionCondition,
			@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

	int queryUserCollectionMapcount(@Param("userCollectionCondition") UserCollectionMap userCollectionCondition);

	UserCollectionMap queryUserCollectionById(@Param("userId") Long userId, @Param("shopId") Long shopId,
			@Param("productId") Long productId,@Param("userCollectionId") Long userCollectionId);

	int deleteUserCollection(Long userCollectionId);

	int insertUserCollection(UserCollectionMap userCollectionMap);
}
