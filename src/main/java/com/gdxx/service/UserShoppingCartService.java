package com.gdxx.service;

import com.gdxx.dto.UserShoppingCartExecution;
import com.gdxx.entity.UserShoppingCart;

public interface UserShoppingCartService {
	UserShoppingCartExecution getUserShopingCartList(UserShoppingCart ShoppingCartCondition, Integer pageIndex,
			Integer pageSize);

	UserShoppingCartExecution addUserShoppingCart(UserShoppingCart ShoppingCart) throws RuntimeException;

	UserShoppingCartExecution removeUserShoppingCart(Long id) throws RuntimeException;

}
