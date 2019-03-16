package com.gdxx.service;

import com.gdxx.dto.ReceivingAddressExecution;
import com.gdxx.entity.ReceivingAddress;

public interface ReceivingAddressService {

	ReceivingAddressExecution getReceivingAddresList(Long userId, Integer pageIndex, Integer pageSize);

	ReceivingAddressExecution getDefaultReceivingAddress(Long userId);

	ReceivingAddressExecution getReceivingAddressById(Long addressId);

	ReceivingAddressExecution addReceivingAddress(ReceivingAddress receivingAddress);

	ReceivingAddressExecution removeReceivingAddress(Long addressId, Long userId);

	ReceivingAddressExecution modifyReceivingAddress(ReceivingAddress receivingAddress);

	ReceivingAddressExecution updateDefaultAddress(Long addressId, Long userId);

}
