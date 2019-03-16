package com.gdxx.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/shopadmin")
public class ShopAdminController {
	/*
	 * 至店铺注册/修改界面
	 */
	@RequestMapping(value = "/shopoperate", method = RequestMethod.GET)
	private String shopOperation() {
		return "/shop/shopOperation";
	}

	/*
	 * 至店铺列表
	 */
	@RequestMapping(value = "/shoplist", method = RequestMethod.GET)
	private String shopList() {
		return "/shop/shopList";
	}

	/*
	 * 至店铺管理
	 */
	@RequestMapping(value = "/shopmanager", method = RequestMethod.GET)
	private String shopManager() {
		return "/shop/shopManager";
	}

	/*
	 * 至店铺中商品种类
	 */
	@RequestMapping(value = "/productcategorymanagement", method = RequestMethod.GET)
	private String productCategoryManagement() {
		return "/shop/productCategoryManage";
	}

	/*
	 * 至店铺中商品新增/修改页面
	 */
	@RequestMapping(value = "/productoperation", method = RequestMethod.GET)
	private String productOperation() {
		return "/shop/productOperation";
	}

	/*
	 * 至商品列表(編輯 上/下 架)
	 */
	@RequestMapping(value = "/productmanager", method = RequestMethod.GET)
	private String productManager() {
		return "/shop/productManager";
	}

	@RequestMapping(value = "/shopauthmanager", method = RequestMethod.GET)
	private String shopAuthManager() {
		return "/shop/shopAuthManager";
	}

	@RequestMapping(value = "/shopauthoperation", method = RequestMethod.GET)
	private String shopAuthOperation() {
		return "/shop/shopAuthOperation";
	}

	@RequestMapping(value = "/productbuyrecord", method = RequestMethod.GET)
	private String productBuyRecord() {
		return "/shop/productBuyRecord";
	}

	@RequestMapping(value = "/userpointlist", method = RequestMethod.GET)
	private String UserPointList() {
		return "/shop/userPointList";
	}

	@RequestMapping(value = "/awardrecieverecord", method = RequestMethod.GET)
	private String awardRecieveRecord() {
		return "/shop/awardRecieveRecord";
	}

	@RequestMapping(value = "/awardmanager", method = RequestMethod.GET)
	private String awardManager() {
		return "/shop/awardManager";
	}

	@RequestMapping(value = "/awardoperate", method = RequestMethod.GET)
	private String awardOperate() {
		return "/shop/awardOperation";
	}

	@RequestMapping(value = "/commentlist4product", method = RequestMethod.GET)
	private String commentList4Product() {
		return "/shop/commentList4Product";
	}

	@RequestMapping(value = "/orderlist", method = RequestMethod.GET)
	private String orderListByShop() {
		return "/shop/orderManager";
	}

}
