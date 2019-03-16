package com.gdxx.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/shopadmin")
public class ShopAdminController {
	/*
	 * ������ע��/�޸Ľ���
	 */
	@RequestMapping(value = "/shopoperate", method = RequestMethod.GET)
	private String shopOperation() {
		return "/shop/shopOperation";
	}

	/*
	 * �������б�
	 */
	@RequestMapping(value = "/shoplist", method = RequestMethod.GET)
	private String shopList() {
		return "/shop/shopList";
	}

	/*
	 * �����̹���
	 */
	@RequestMapping(value = "/shopmanager", method = RequestMethod.GET)
	private String shopManager() {
		return "/shop/shopManager";
	}

	/*
	 * ����������Ʒ����
	 */
	@RequestMapping(value = "/productcategorymanagement", method = RequestMethod.GET)
	private String productCategoryManagement() {
		return "/shop/productCategoryManage";
	}

	/*
	 * ����������Ʒ����/�޸�ҳ��
	 */
	@RequestMapping(value = "/productoperation", method = RequestMethod.GET)
	private String productOperation() {
		return "/shop/productOperation";
	}

	/*
	 * ����Ʒ�б�(��݋ ��/�� ��)
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
