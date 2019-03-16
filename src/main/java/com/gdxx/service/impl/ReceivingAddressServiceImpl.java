package com.gdxx.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdxx.dao.ReceivingAddressDao;
import com.gdxx.dto.ReceivingAddressExecution;
import com.gdxx.entity.ReceivingAddress;
import com.gdxx.enums.ReceivingAddressStateEnum;
import com.gdxx.service.ReceivingAddressService;
import com.gdxx.util.PageCalculator;

@Service
public class ReceivingAddressServiceImpl implements ReceivingAddressService {
	@Autowired
	private ReceivingAddressDao receivingAddressDao;

	@Override
	public ReceivingAddressExecution getReceivingAddresList(Long userId, Integer pageIndex, Integer pageSize) {
		if (userId != null && pageIndex != null && pageSize != null) {
			int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
			List<ReceivingAddress> receAddressList = receivingAddressDao.queryReceivingAddressList(userId, rowIndex,
					pageSize);
			int count = receivingAddressDao.queryReceivingAddressCount(userId);
			if (receAddressList != null && count >= 0) {
				ReceivingAddressExecution rae = new ReceivingAddressExecution();
				rae.setReceivingAddressList(receAddressList);
				rae.setCount(count);
				rae.setStateInfo(ReceivingAddressStateEnum.SUCCESS.getStateInfo());
				return rae;
			}
		}
		return null;
	}

	@Override
	public ReceivingAddressExecution getDefaultReceivingAddress(Long userId) {
		ReceivingAddressExecution rae = new ReceivingAddressExecution();
		ReceivingAddress ra = receivingAddressDao.queryDefaultReceivingAddress(userId);
		if (ra != null) {
			rae.setReceivingAddress(ra);
			rae.setStateInfo(ReceivingAddressStateEnum.SUCCESS.getStateInfo());
			return rae;
		}
		return null;
	}

	@Override
	public ReceivingAddressExecution addReceivingAddress(ReceivingAddress receivingAddress) {
		if (receivingAddress != null && receivingAddress.getUser() != null
				&& receivingAddress.getContactPhone() != null) {
			receivingAddress.setCreateTime(new Date());
			receivingAddress.setPriority(0);
			try {
				int effNum = receivingAddressDao.insertReceivingAddress(receivingAddress);
				if (effNum <= 0) {
					return new ReceivingAddressExecution(ReceivingAddressStateEnum.INNER_ERROR);
				}
				return new ReceivingAddressExecution(ReceivingAddressStateEnum.SUCCESS);
			} catch (Exception e) {
				e.getMessage();
				throw new RuntimeException("新增地址时发生异常");
			}

		} else {
			return new ReceivingAddressExecution(ReceivingAddressStateEnum.NULL_INFO);
		}

	}

	@Override
	public ReceivingAddressExecution removeReceivingAddress(Long addressId, Long userId) {
		if (addressId != null && userId != null) {
			try {
				int effNum = receivingAddressDao.deleteReceivingAddressById(addressId, userId);
				if (effNum <= 0) {
					return new ReceivingAddressExecution(ReceivingAddressStateEnum.INNER_ERROR);
				}
				return new ReceivingAddressExecution(ReceivingAddressStateEnum.SUCCESS);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}

		} else {
			return new ReceivingAddressExecution(ReceivingAddressStateEnum.NULL_ID);
		}

	}

	@Override
	public ReceivingAddressExecution modifyReceivingAddress(ReceivingAddress receivingAddress) {
		if (receivingAddress != null && receivingAddress.getUser() != null
				&& receivingAddress.getContactPhone() != null) {
			receivingAddress.setUpdateTime(new Date());
			try {
				int effNum = receivingAddressDao.updateReceivingAddress(receivingAddress);
				if (effNum <= 0) {
					return new ReceivingAddressExecution(ReceivingAddressStateEnum.INNER_ERROR);
				}
				return new ReceivingAddressExecution(ReceivingAddressStateEnum.SUCCESS);
			} catch (Exception e) {
				e.getMessage();
				throw new RuntimeException("修改地址时发生异常");
			}
		} else {
			return new ReceivingAddressExecution(ReceivingAddressStateEnum.NULL_INFO);
		}
	}

	@Override
	public ReceivingAddressExecution updateDefaultAddress(Long addressId, Long userId) {
		if (addressId != null) {
			try {
				receivingAddressDao.updateDefaultSetZero(userId);
				int effNum2 = receivingAddressDao.updateSetDefaultToOne(addressId);
				if (effNum2 <= 0) {
					return new ReceivingAddressExecution(ReceivingAddressStateEnum.INNER_ERROR);
				}
				return new ReceivingAddressExecution(ReceivingAddressStateEnum.SUCCESS);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		} else {
			new ReceivingAddressExecution(ReceivingAddressStateEnum.NULL_ID);
		}
		return new ReceivingAddressExecution(ReceivingAddressStateEnum.INNER_ERROR);

	}

	@Override
	public ReceivingAddressExecution getReceivingAddressById(Long addressId) {
		if (addressId != null) {
			ReceivingAddress address = receivingAddressDao.queryReceivingAddressById(addressId);
			if (address != null && address.getAddressId() != null) {
				ReceivingAddressExecution rae = new ReceivingAddressExecution();
				rae.setReceivingAddress(address);
				rae.setStateInfo(ReceivingAddressStateEnum.SUCCESS.getStateInfo());
				return rae;
			}
		}
		return null;
	}

}
