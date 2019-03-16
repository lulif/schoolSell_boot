package com.gdxx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdxx.entity.UserShoppingCart;

public interface UserShoppingCartDao {
	List<UserShoppingCart> queryUserShoppingCartList(
			@Param("shoppingCartCondition") UserShoppingCart shoppingCartCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	int queryCartProductCount(@Param("shoppingCartCondition") UserShoppingCart shoppingCartCondition);

	UserShoppingCart queryCartProductById(@Param("id") Long id, @Param("shopId") Long shopId,
			@Param("productId") Long productId, @Param("userId") Long userId);

	int deleteCartProduct(Long id);

	int insertCartProduct(UserShoppingCart CartProduct);
}
