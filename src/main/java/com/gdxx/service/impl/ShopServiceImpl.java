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
		// ��ֵ�ж�
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		// ʵ�帳��ֵ
		try {
			shop.setPriority(0);
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			// ��ӵ���
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum <= 0) {
				throw new ShopOperationException("���̴���ʧ��");
			} else {
				if (shopImg != null) {
					try {// �洢ͼƬ
						addShopImg(shop, shopImg);
					} catch (Exception e) {
						throw new ShopOperationException("addShopImg error:" + e.getMessage());
					}
					// ���µ��̵�ͼƬ��ַ
					effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						throw new ShopOperationException("����ͼƬ��ַʧ��");
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
		// 1.�ж��Ƿ�Ҫ�޸�ͼƬ
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
				// 2.���µ�����Ϣ
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
