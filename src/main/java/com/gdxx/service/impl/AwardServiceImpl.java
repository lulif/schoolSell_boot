package com.gdxx.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.gdxx.Exceptions.AwardException;
import com.gdxx.dao.AwardDao;
import com.gdxx.dto.AwardExecution;
import com.gdxx.entity.Award;
import com.gdxx.enums.AwardStateEnum;
import com.gdxx.service.AwardService;
import com.gdxx.util.ImageUtil;
import com.gdxx.util.PageCalculator;
import com.gdxx.util.PathUtil;

@Service
public class AwardServiceImpl implements AwardService {
	@Autowired
	private AwardDao awardDao;

	@Override
	public AwardExecution getAwardList(Award awardCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		awardCondition.setExpireTime(new Date());
		List<Award> awardList = awardDao.queryAwardList(awardCondition, rowIndex, pageSize);
		int count = awardDao.queryAwardCount(awardCondition);
		AwardExecution ae = new AwardExecution();
		ae.setAwardList(awardList);
		ae.setCount(count);
		return ae;
	}

	@Override
	public Award getAwardById(long awardId) {
		return awardDao.queryAwardByAwardId(awardId);
	}

	@Override
	@Transactional
	public AwardExecution addAward(Award award, CommonsMultipartFile thumbnail) {
		if (award != null && award.getShopId() != null) {
			award.setLastEditTime(new Date());
			award.setCreateTime(new Date());
			award.setEnableStatus(1);
			if (thumbnail != null) {
				addThumbnail(award, thumbnail);
			}
			try {
				int effNum = awardDao.insertAward(award);
				if (effNum <= 0) {
					throw new AwardException("添加奖品失败");
				}
				return new AwardExecution(AwardStateEnum.SUCCESS, award);
			} catch (Exception e) {
				throw new AwardException("添加奖品失败" + e.toString());
			}
		} else {
			return new AwardExecution(AwardStateEnum.EMPTY);
		}

	}

	@Override
	@Transactional
	public AwardExecution modifyAward(Award award, CommonsMultipartFile thumbnail) {
		if (award != null && award.getShopId() != null) {
			award.setLastEditTime(new Date());
			if (thumbnail != null) {
				Award tempAward = awardDao.queryAwardByAwardId(award.getAwardId());
				if (tempAward.getAwardImg() != null) {
					ImageUtil.deleteFileOrPath(tempAward.getAwardImg());
				}
				addThumbnail(award, thumbnail);
			}
			try {
				int effNum = awardDao.updateAward(award);
				if (effNum <= 0) {
					throw new AwardException("更新奖品失败");
				}
				return new AwardExecution(AwardStateEnum.SUCCESS, award);
			} catch (Exception e) {
				throw new AwardException("更新奖品失败" + e.toString());
			}
		} else {
			return new AwardExecution(AwardStateEnum.EMPTY);
		}

	}

	private void addThumbnail(Award award, CommonsMultipartFile thumbnail) {
		String dest = PathUtil.getShopImagePath(award.getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest, false);
		award.setAwardImg(thumbnailAddr);
	}

}
