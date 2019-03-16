package com.gdxx.web.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdxx.dto.CustomerCommentExecution;
import com.gdxx.entity.CustomerComment;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.Product;
import com.gdxx.entity.Shop;
import com.gdxx.enums.CustomerCommentStateEnum;
import com.gdxx.service.CustomerCommentService;
import com.gdxx.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class CustomerCommentController {
	@Autowired
	private CustomerCommentService customerCommentService;

	@RequestMapping(value = "/addcomment", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addCustomerComment(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String comment = HttpServletRequestUtil.getString(request, "customerComment");
		ObjectMapper mapper = new ObjectMapper();
		CustomerComment customerComment = null;
		if (comment != null) {
			try {
				customerComment = mapper.readValue(comment, CustomerComment.class);
				// PersonInfo customer=(PersonInfo)request.getSession().getAttribute("user");
				// 测试数据
				PersonInfo customer = new PersonInfo();
				customer.setUserId(1742319L);
				customerComment.setCustomer(customer);
				CustomerCommentExecution cce = customerCommentService.addCustomerComment(customerComment);
				if (cce.getStateInfo() == CustomerCommentStateEnum.SUCCESS.getStateInfo()) {
					modelMap.put("success", true);
					return modelMap;
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "评论信息为空");
		}
		return modelMap;
	}

	@RequestMapping(value = "/listcustomercomment", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listCustomerComment(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Integer pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		Integer pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		String productName = HttpServletRequestUtil.getString(request, "productName");

		Long productId = HttpServletRequestUtil.getLong(request, "productId");
		int isBack = HttpServletRequestUtil.getInt(request, "isback");
		CustomerComment comment = new CustomerComment();
		Product p = new Product();
		Shop s = new Shop();
		if (productId == -1L && isBack == -1) {
			// PersonInfo customer=(PersonInfo)request.getSession().getAttribute("user");
			// 测试数据
			PersonInfo customer = new PersonInfo();
			customer.setUserId(1742319L);
			comment.setCustomer(customer);
		}
		if (productId != -1) {
			p.setProductId(productId);
			comment.setProduct(p);
		}
		if (isBack != -1) {
			Shop shop = (Shop) request.getSession().getAttribute("currentShop");
			Long shopId = shop.getShopId();
			s.setShopId(shopId);
			comment.setShop(s);
		}
		if (productName != null) {

			p.setProductName(productName);
			comment.setProduct(p);
		}
		if (comment != null && pageIndex > -1 && pageSize > -1) {
			CustomerCommentExecution cce = customerCommentService.getCustomerCommentList(comment, pageIndex, pageSize);
			if (cce.getStateInfo() == CustomerCommentStateEnum.SUCCESS.getStateInfo()) {
				modelMap.put("success", true);
				modelMap.put("customerCommentList", cce.getCustomerCommentList());
				modelMap.put("count", cce.getCount());
				return modelMap;
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "查询错误");
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "所需数据为空");
		}
		return modelMap;
	}

	@RequestMapping(value = "/deletecustomercomment", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> deleteCustomerComment(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long customerCommentId = HttpServletRequestUtil.getLong(request, "customerCommentId");
		if (customerCommentId != -1L) {
			try {
				CustomerCommentExecution cce = customerCommentService.removeCustomerCommentById(customerCommentId);
				if (cce.getStateInfo() == CustomerCommentStateEnum.SUCCESS.getStateInfo()) {
					modelMap.put("success", true);
					return modelMap;
				} else {
					modelMap.put("success", false);
				}

			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "所需数据为空");
		}
		return modelMap;
	}

}
