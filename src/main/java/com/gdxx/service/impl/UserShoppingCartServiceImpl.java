package com.gdxx.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdxx.dao.PersonInfoDao;
import com.gdxx.dao.ProductDao;
import com.gdxx.dao.ShopDao;
import com.gdxx.dao.UserShoppingCartDao;
import com.gdxx.dto.UserShoppingCartExecution;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.Product;
import com.gdxx.entity.Shop;
import com.gdxx.entity.UserShoppingCart;
import com.gdxx.enums.UserShoppingCartStateEnum;
import com.gdxx.service.UserShoppingCartService;
import com.gdxx.util.PageCalculator;

@Service
public class UserShoppingCartServiceImpl implements UserShoppingCartService {
	@Autowired
	private PersonInfoDao personInfoDao;
	@Autowired
	private ShopDao shopDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private UserShoppingCartDao userShoppingCartDao;

	@Override
	public UserShoppingCartExecution getUserShopingCartList(UserShoppingCart shoppingCartCondition, Integer pageIndex,
			Integer pageSize) {
		if (shoppingCartCondition != null && pageSize != null && pageIndex != null) {
			int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
			List<UserShoppingCart> UserShoppingCartList = userShoppingCartDao
					.queryUserShoppingCartList(shoppingCartCondition, rowIndex, pageSize);
			int count = userShoppingCartDao.queryCartProductCount(shoppingCartCondition);
			UserShoppingCartExecution cartExecution = new UserShoppingCartExecution();
			cartExecution.setCount(count);
			cartExecution.setUserShoppingCartList(UserShoppingCartList);
			cartExecution.setStateInfo(UserShoppingCartStateEnum.SUCCESS.getStateInfo());
			return cartExecution;
		}
		return null;
	}

	@Override
	public UserShoppingCartExecution addUserShoppingCart(UserShoppingCart shoppingCart) throws RuntimeException {
		if (shoppingCart.getUser() != null && shoppingCart.getShop() != null && shoppingCart.getProduct() != null) {
			shoppingCart.setCreateTime(new Date());
			try {
				Long userId = shoppingCart.getUser().getUserId();
				Long shopId = shoppingCart.getShop().getShopId();
				Long productId = shoppingCart.getProduct().getProductId();
				PersonInfo p = personInfoDao.queryPersonInfoById(userId);
				Shop shop = shopDao.queryByShopId(shopId);
				Product product = productDao.queryProductById(productId);
				if (p != null && shop != null && product != null) {
					UserShoppingCart tempUserShoppingCart = userShoppingCartDao.queryCartProductById(null, shopId,
							productId, userId);
					if (tempUserShoppingCart != null) {
						return new UserShoppingCartExecution(UserShoppingCartStateEnum.HAD_ADD);
					}
					int effNum = userShoppingCartDao.insertCartProduct(shoppingCart);
					if (effNum <= 0) {
						return new UserShoppingCartExecution(UserShoppingCartStateEnum.INNER_ERROR);
					}
				} else {
					return new UserShoppingCartExecution(UserShoppingCartStateEnum.NULL_INFO);
				}
			} catch (Exception e) {
				e.toString();
				throw new RuntimeException();
			}
			return new UserShoppingCartExecution(UserShoppingCartStateEnum.SUCCESS);
		}
		return new UserShoppingCartExecution(UserShoppingCartStateEnum.NULL_INFO);
	}

	@Override
	public UserShoppingCartExecution removeUserShoppingCart(Long id) throws RuntimeException {

		if (id != null) {
			try {
				UserShoppingCart userShoppingCart = userShoppingCartDao.queryCartProductById(id,null,null,null);
				if (userShoppingCart == null) {
					return new UserShoppingCartExecution(UserShoppingCartStateEnum.INNER_ERROR);
				}
				int effNum = userShoppingCartDao.deleteCartProduct(id);
				if (effNum <= 0) {
					return new UserShoppingCartExecution(UserShoppingCartStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new RuntimeException("抛出异常失败,从购物车中移除商品失败");
			}
			return new UserShoppingCartExecution(UserShoppingCartStateEnum.SUCCESS);
		} else {
			return new UserShoppingCartExecution(UserShoppingCartStateEnum.NULL_ID);
		}
	}

}
