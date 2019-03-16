package com.gdxx.web.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdxx.dto.UserShoppingCartExecution;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.Product;
import com.gdxx.entity.UserShoppingCart;
import com.gdxx.enums.UserShoppingCartStateEnum;
import com.gdxx.service.UserShoppingCartService;
import com.gdxx.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class UserShoppingCartController {
	@Autowired
	private UserShoppingCartService userShoppingCartService;

	@RequestMapping(value = "/addintoshopppingcart", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addIntoShoppingCart(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String userShoppingCartMsg = HttpServletRequestUtil.getString(request, "userShoppingCart");
		ObjectMapper mapper = new ObjectMapper();
		UserShoppingCart shoppingCart = null;
		try {
			shoppingCart = mapper.readValue(userShoppingCartMsg, UserShoppingCart.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		// PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
		PersonInfo user = new PersonInfo();
		user.setUserId(1742319L);
		shoppingCart.setUser(user);
		if (shoppingCart != null) {
			UserShoppingCartExecution usce = userShoppingCartService.addUserShoppingCart(shoppingCart);
			if (usce.getStateInfo() == UserShoppingCartStateEnum.SUCCESS.getStateInfo()) {
				modelMap.put("success", true);
				return modelMap;
			}
			if (usce.getStateInfo() == UserShoppingCartStateEnum.HAD_ADD.getStateInfo()) {
				modelMap.put("success", false);
				modelMap.put("had_add", true);
			}
		}
		modelMap.put("success", false);
		return modelMap;
	}

	// url{}中是必传的
	@RequestMapping(value = "/listcartproduct/{pageIndex}/{pageSize}/**", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listCartProduct(@PathVariable("pageIndex") Integer pageIndex,
			@PathVariable("pageSize") Integer pageSize, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String productName = HttpServletRequestUtil.getString(request, "productName");
		UserShoppingCart shoppingCart = new UserShoppingCart();
		// PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
		PersonInfo user = new PersonInfo();
		user.setUserId(1742319L);
		shoppingCart.setUser(user);

		if (productName != null) {
			Product product = new Product();
			product.setProductName(productName);
			shoppingCart.setProduct(product);
		}
		UserShoppingCartExecution usce = null;
		if (pageSize > -1 && pageIndex > -1) {
			usce = userShoppingCartService.getUserShopingCartList(shoppingCart, pageIndex, pageSize);
			if (usce.getStateInfo() == UserShoppingCartStateEnum.SUCCESS.getStateInfo()) {
				modelMap.put("success", true);
				modelMap.put("cartProductList", usce.getUserShoppingCartList());
				modelMap.put("count", usce.getCount());
				return modelMap;
			}
		}
		modelMap.put("success", false);
		modelMap.put("errMsg", usce.getStateInfo());
		return modelMap;
	}

	@RequestMapping(value = "/deletefromcart/{id}", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> deleteFromCart(@PathVariable("id") Long id) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (id != null) {
			try {
				UserShoppingCartExecution usce = userShoppingCartService.removeUserShoppingCart(id);
				if (usce.getStateInfo() == UserShoppingCartStateEnum.SUCCESS.getStateInfo()) {
					modelMap.put("success", true);
					return modelMap;
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "id为空");
		}
		return modelMap;
	}

}
