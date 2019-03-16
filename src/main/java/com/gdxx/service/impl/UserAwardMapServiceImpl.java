package com.gdxx.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdxx.dao.UserAwardMapDao;
import com.gdxx.dao.UserShopMapDao;
import com.gdxx.dto.UserAwardMapExecution;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.UserAwardMap;
import com.gdxx.entity.UserShopMap;
import com.gdxx.enums.UserAwardMapStateEnum;
import com.gdxx.service.UserAwardMapService;
import com.gdxx.util.PageCalculator;

@Service
public class UserAwardMapServiceImpl implements UserAwardMapService {
	@Autowired
	private UserAwardMapDao userAwardMapDao;
	@Autowired
	private UserShopMapDao userShopMapDao;

	@Override
	public UserAwardMapExecution listUserAwardMap(UserAwardMap userAwardCondition, Integer pageIndex,
			Integer pageSize) {
		if (userAwardCondition != null && pageIndex != null && pageSize != null) {
			int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
			List<UserAwardMap> userAwardMapList = userAwardMapDao.queryUserAwardMapList(userAwardCondition, rowIndex,
					pageSize);
			int count = userAwardMapDao.queryUserAwardMapCount(userAwardCondition);
			UserAwardMapExecution ue = new UserAwardMapExecution();
			ue.setUserAwardMapList(userAwardMapList);
			ue.setCount(count);
			return ue;
		} else {
			return null;
		}
	}

	@Override
	public UserAwardMap getUserAwardMapById(long userAwardMapId) {
		return userAwardMapDao.queryUserAwardMapById(userAwardMapId);
	}

	@Override
	@Transactional
	public UserAwardMapExecution addUserAwardMap(UserAwardMap userAwardMap) throws RuntimeException {
		if (userAwardMap != null && userAwardMap.getUser().getUserId() != null
				&& userAwardMap.getUser().getUserId() != null) {
			userAwardMap.setCreateTime(new Date());
			PersonInfo operator = new PersonInfo();
			operator.setUserId(1L);
			userAwardMap.setOperator(operator);
			try {
				// �һ���Ʒ����Ļ��ֲ�����null�Ҵ���0
				if (userAwardMap.getPoint() != null && userAwardMap.getPoint() > 0) {

					UserShopMap userShopMap = userShopMapDao.queryUserShopMap(userAwardMap.getUser().getUserId(),
							userAwardMap.getShop().getShopId());
					// ��λ�˿��Ƿ�����ҵ�
					if (userShopMap != null) {
						if (userShopMap.getPoint() >= userAwardMap.getPoint()) {
							userShopMap.setPoint(userShopMap.getPoint() - userAwardMap.getPoint());
							// �һ���֮����� �˿��ڴ˵��̵Ļ�����
							int effNum1 = userShopMapDao.updateUserShopMapPoint(userShopMap);
							int effNum2 = userAwardMapDao.insertUserAwardMap(userAwardMap);
							if (effNum2 <= 0) {
								throw new RuntimeException("��ȡ����ʧ��");
							}
							if (effNum1 <= 0) {
								throw new RuntimeException("���»�����Ϣʧ��");
							}

						} else {
							throw new RuntimeException("���ֲ����޷���ȡ");
						}
					} else {
						throw new RuntimeException("�ڱ�����û�л��֣��޷��Ի���Ʒ");
					}
				}
			} catch (Exception e) {
				throw new RuntimeException("��ȡ����ʧ��:" + e.toString());
			}
			return new UserAwardMapExecution(UserAwardMapStateEnum.SUCCESS, userAwardMap);
		} else {
			return new UserAwardMapExecution(UserAwardMapStateEnum.NULL_USERAWARD_INFO);
		}

	}

	@Override
	@Transactional
	public UserAwardMapExecution modifyUserAwardMap(UserAwardMap userAwardMap) throws RuntimeException {

		if (userAwardMap != null && userAwardMap.getUsedStatus() != null
				&& userAwardMap.getAward().getAwardId() != null) {
			try {
				// �޸Ŀ���״̬
				int effectedNum = userAwardMapDao.updateUserAwardMap(userAwardMap);
				if (effectedNum <= 0) {
					return new UserAwardMapExecution(UserAwardMapStateEnum.INNER_ERROR);
				} else {
					return new UserAwardMapExecution(UserAwardMapStateEnum.SUCCESS, userAwardMap);
				}
			} catch (Exception e) {
				throw new RuntimeException("modifyUserAwardMap error: " + e.getMessage());
			}
		} else {
			return new UserAwardMapExecution(UserAwardMapStateEnum.NULL_USERAWARD_ID);
		}
	}

}
