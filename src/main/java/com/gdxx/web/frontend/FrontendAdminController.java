package com.gdxx.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/frontend")
public class FrontendAdminController {
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	private String index() {
		return "/frontend/index";
	}

	@RequestMapping(value = "/gotolistshops", method = RequestMethod.GET)
	private String listShops() {
		return "/frontend/listShops";
	}

	@RequestMapping(value = "/gotodetailshops", method = RequestMethod.GET)
	private String detailShops() {
		return "/frontend/detailShops";
	}

	@RequestMapping(value = "/gotodetailproducts", method = RequestMethod.GET)
	private String gotoDetailProducts() {
		return "/frontend/detailProducts";
	}

	@RequestMapping(value = "/gotoawardlist", method = RequestMethod.GET)
	private String gotoAwardList() {
		return "/frontend/listAwards";
	}

	@RequestMapping(value = "/pointrecord", method = RequestMethod.GET)
	private String pointRecord() {
		return "/frontend/pointRecord";
	}

	@RequestMapping(value = "/mypoint", method = RequestMethod.GET)
	private String myPoint() {
		return "/frontend/myPoint";
	}

	@RequestMapping(value = "/detailaward", method = RequestMethod.GET)
	private String detailAward() {
		return "/frontend/detailAward";
	}

	@RequestMapping(value = "/buyrecord", method = RequestMethod.GET)
	private String buyRecord() {
		return "/frontend/buyRecord";
	}

	@RequestMapping(value = "/mycollection", method = RequestMethod.GET)
	private String myCollection() {
		return "/frontend/myCollection";
	}

	@RequestMapping(value = "/gotocomment", method = RequestMethod.GET)
	private String goToComment() {
		return "/frontend/customerComment";
	}

	@RequestMapping(value = "/mycomment", method = RequestMethod.GET)
	private String myComment() {
		return "/frontend/myComment";
	}

	@RequestMapping(value = "/commentbyproductid", method = RequestMethod.GET)
	private String commentByProductId() {
		return "/frontend/commentByProductId";
	}

	@RequestMapping(value = "/myshoppingcart", method = RequestMethod.GET)
	private String myShoppingCart() {
		return "/frontend/myShoppingCart";
	}

	@RequestMapping(value = "/myreceivingaddress", method = RequestMethod.GET)
	private String myReceivingAddress() {
		return "/frontend/myReceivingAddress";
	}

	@RequestMapping(value = "/addreceivingaddress", method = RequestMethod.GET)
	private String addReceivingAddress() {
		return "/frontend/addReceivingAddress";
	}

	@RequestMapping(value = "/addorder", method = RequestMethod.GET)
	private String addOrder() {
		return "/frontend/addOrder";
	}

	@RequestMapping(value = "/myorder", method = RequestMethod.GET)
	private String myOrder() {
		return "/frontend/myOrder";
	}

}
