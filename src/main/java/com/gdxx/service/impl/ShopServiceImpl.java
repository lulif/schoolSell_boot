package com.gdxx.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.gdxx.Exceptions.ShopOperationException;
import com.gdxx.dao.ShopDao;
import com.gdxx.dto.ShopExecution;
import com.gdxx.entity.Shop;
import com.gdxx.enums.ShopStateEnum;
import com.gdxx.service.ShopService;
import com.gdxx.util.ImageUtil;
import com.gdxx.util.PageCalculator;
import com.gdxx.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {
	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) {
		// ø’÷µ≈–∂œ
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		//  µÃÂ∏≥≥ı÷µ
		try {
			shop.setPriority(0);
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			// ÃÌº”µÍ∆Ã
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum <= 0) {
				throw new ShopOperationException("µÍ∆Ã¥¥Ω® ß∞‹");
			} else {
				if (shopImg != null) {
					try {// ¥Ê¥¢Õº∆¨
						addShopImg(shop, shopImg);
					} catch (Exception e) {
						throw new ShopOperationException("addShopImg error:" + e.getMessage());
					}
					// ∏¸–¬µÍ∆ÃµƒÕº∆¨µÿ÷∑
					effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						throw new ShopOperationException("∏¸–¬Õº∆¨µÿ÷∑ ß∞‹");
					}
				}
			}
		} catch (Exception e) {
			throw new ShopOperationException("addShop error" + e.getMessage());
		}

		return new ShopExecution(ShopStateEnum.CHECK, shop);
	}

	private void addShopImg(Shop shop, CommonsMultipartFile shopImg) {
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImdAddr = ImageUtil.generateThumbnail(shopImg, dest,false);
		shop.setShopImg(shopImdAddr);
	}

	@Override
	public Shop getShopByShopId(long shopId) {

		return shopDao.queryByShopId(shopId);
	}

	@Override
	@Transactional
	public ShopExecution modifyShop(Shop shop, CommonsMultipartFile shopImgInputStream) throws ShopOperationException {
		// 1.≈–∂œ «∑Ò“™–ﬁ∏ƒÕº∆¨
		try {
			if (shop == null || shop.getShopId() == null) {
				return new ShopExecution(ShopStateEnum.NULL_SHOP);
			} else {
				if (shopImgInputStream != null) {
					Shop tempShop = shopDao.queryByShopId(shop.getShopId());
					if (tempShop.getShopImg() != null) {
						ImageUtil.deleteFileOrPath(tempShop.getShopImg());
					}
					addShopImg(shop, shopImgInputStream);
				}
				// 2.∏¸–¬µÍ∆Ã–≈œ¢
				shop.setLastEditTime(new Date());
				int effectedNum = shopDao.updateShop(shop);
				if (effectedNum <= 0) {
					return new ShopExecution(ShopStateEnum.INNER_ERROR);
				} else {
					shop = shopDao.queryByShopId(shop.getShopId());
					return new ShopExecution(ShopStateEnum.SUCCESS, shop);
				}
			}
		} catch (Exception e) {
			throw new ShopOperationException("modifyShop error:" + e.getMessage());
		}

	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int count = shopDao.queryShopCount(shopCondition);
		ShopExecution se = new ShopExecution();
		if (shopList != null) {
			se.setShopList(shopList);
			se.setCount(count);
		} else {
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;
	}

}
