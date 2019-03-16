package com.gdxx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdxx.entity.ReceivingAddress;

public interface ReceivingAddressDao {
	List<ReceivingAddress> queryReceivingAddressList(@Param("userId") Long userId, @Param("rowIndex") Integer rowIndex,
			@Param("pageSize") Integer pageSize);

	int queryReceivingAddressCount(@Param("userId") Long userId);

	ReceivingAddress queryDefaultReceivingAddress(Long userId);

	ReceivingAddress queryReceivingAddressById(Long addressId);

	int updateReceivingAddress(@Param("receivingAddress") ReceivingAddress receivingAddress);

	int updateSetDefaultToOne(Long addressId);

	int updateDefaultSetZero(Long userId);

	int insertReceivingAddress(@Param("receivingAddress") ReceivingAddress receivingAddress);

	int deleteReceivingAddressById(@Param("addressId") Long addressId, @Param("userId") Long userId);

}
