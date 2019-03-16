package com.gdxx.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdxx.dao.CustomerCommentDao;
import com.gdxx.dao.UserProductMapDao;
import com.gdxx.dto.CustomerCommentExecution;
import com.gdxx.entity.CustomerComment;
import com.gdxx.enums.CustomerCommentStateEnum;
import com.gdxx.service.CustomerCommentService;
import com.gdxx.util.PageCalculator;

@Service
public class CustomerCommentServiceImpl implements CustomerCommentService {
	@Autowired
	private CustomerCommentDao customerCommentDao;
	@Autowired
	private UserProductMapDao userProductMapDao;

	@Override
	public CustomerCommentExecution getCustomerCommentList(CustomerComment comment, Integer pageIndex,
			Integer pageSize) {
		if (comment != null && pageIndex != null && pageSize != null) {
			int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
			List<CustomerComment> commentList = customerCommentDao.queryCustomerCommentList(comment, rowIndex,
					pageSize);
			int count = customerCommentDao.queryCustomerCommentCount(comment);
			CustomerCommentExecution cce = new CustomerCommentExecution();
			cce.setCount(count);
			cce.setCustomerCommentList(commentList);
			cce.setStateInfo(CustomerCommentStateEnum.SUCCESS.getStateInfo());
			return cce;
		}
		return null;
	}

	@Override
	@Transactional
	public CustomerCommentExecution addCustomerComment(CustomerComment comment) throws RuntimeException {
		if (comment != null && comment.getCustomer() != null && comment.getShop() != null
				&& comment.getProduct() != null) {
			comment.setCreateTime(new Date());
			try {
				int effNum1 = customerCommentDao.insertCustomerComment(comment);
				int effNum2 = userProductMapDao.modifyUserProductMapIsComment(comment.getUserProductId(), 1);
				if (effNum1 <= 0 && effNum2 <= 0) {
					return new CustomerCommentExecution(CustomerCommentStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				e.getMessage();
				throw new RuntimeException();
			}
			return new CustomerCommentExecution(CustomerCommentStateEnum.SUCCESS);
		} else {
			return new CustomerCommentExecution(CustomerCommentStateEnum.EMPTY);
		}
	}

	@Override
	public CustomerCommentExecution removeCustomerCommentById(Long customerCommentId) throws RuntimeException {
		if (customerCommentId != null) {
			try {
				int effNum = customerCommentDao.deleteCustomerCommentById(customerCommentId);
				if (effNum <= 0) {
					return new CustomerCommentExecution(CustomerCommentStateEnum.INNER_ERROR);

				}
			} catch (Exception e) {
				e.getMessage();
				throw new RuntimeException();
			}
			return new CustomerCommentExecution(CustomerCommentStateEnum.SUCCESS);
		} else {
			return new CustomerCommentExecution(CustomerCommentStateEnum.EMPTY);
		}
	}

	@Override
	public Double getAvgpointByProIdAndshopId(Long productId, Long shopId) {
		return customerCommentDao.queryAvgpointByProIdAndshopId(productId, shopId);
	}

}
