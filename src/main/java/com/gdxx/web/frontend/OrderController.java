package com.gdxx.web.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdxx.dto.OrderExecution;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.ReceivingAddress;
import com.gdxx.enums.OrderStateEnum;
import com.gdxx.service.OrderService;
import com.gdxx.util.HttpServletRequestUtil;

@Controller()
@RequestMapping("/frontend")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/insertorderinfo", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> insertOrderInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		Double totalMon = HttpServletRequestUtil.getDouble(request, "totalMon");
		String orderMsg = HttpServletRequestUtil.getString(request, "orderMessage");
		String addressMsg = HttpServletRequestUtil.getString(request, "defaultAddres");
		Boolean isCancel = HttpServletRequestUtil.getBoolean(request, "isCancel");
		ReceivingAddress address = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			address = mapper.readValue(addressMsg, ReceivingAddress.class);
		} catch (Exception e) {
			modelMap.put("succcess", false);
			modelMap.put("errMsg", e.getMessage());
		}
		// PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
		PersonInfo user = new PersonInfo();
		user.setUserId(1742319L);
		OrderExecution orderExecution = null;
		if (user != null && user.getUserId() != null && totalMon != null && orderMsg != null && address != null) {
			if (isCancel) {
				orderExecution = orderService.addOrderNotPayed(address, user, totalMon, orderMsg);
			} else {
				orderExecution = orderService.addOrderHadPayed(address, user, totalMon, orderMsg);
			}

			if (orderExecution.getStateInfo() == OrderStateEnum.SUCCESS.getStateInfo()) {
				modelMap.put("success", true);
				return modelMap;
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", OrderStateEnum.INNER_ERROR.getStateInfo());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", OrderStateEnum.EMPTY.getStateInfo());
		}
		return modelMap;
	}

	@RequestMapping(value = "/getordermsglist/{orderStatus}", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getOrderMsgList(HttpServletRequest request,
			@PathVariable("orderStatus") Integer orderStatus) {
		Map<String, Object> modelMap = new HashMap<>();
		// PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
		PersonInfo user = new PersonInfo();
		user.setUserId(1742319L);
		OrderExecution orderExecution = null;
		if (orderStatus != null && user != null && user.getUserId() != null) {
			orderExecution = orderService.getOrderListByIdAndStatus(null, user.getUserId(), orderStatus);
			if (orderExecution.getStateInfo() == OrderStateEnum.SUCCESS.getStateInfo()) {
				modelMap.put("success", true);
				modelMap.put("orderList", orderExecution.getOrderList());
				modelMap.put("orderProductMap", orderExecution.getOrderIdProductListMap());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", OrderStateEnum.INFO_NULL);
		}
		modelMap.put("success", false);
		modelMap.put("errMsg", orderExecution.getStateInfo());
		return modelMap;
	}

	@RequestMapping(value = "/modifyorderstatus", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyOrderStatus(@RequestParam("orderId") String orderId,
			@RequestParam("operationType") String operationType) {
		Map<String, Object> modelMap = new HashMap<>();
		OrderExecution orderExecution = null;
		if (orderId != null && operationType != null) {
			try {
				orderExecution = orderService.modifyOrderStatus(orderId, operationType);
				if (orderExecution.getStateInfo() == OrderStateEnum.SUCCESS.getStateInfo()) {
					modelMap.put("success", true);
					return modelMap;
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", OrderStateEnum.INFO_NULL);
		}
		modelMap.put("success", false);
		modelMap.put("errMsg", orderExecution.getStateInfo());
		return modelMap;
	}

	@RequestMapping(value = "/pay4orderhadcancel", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> pay4OrderHadCancel(HttpServletRequest request,
			@RequestParam("orderId") String orderId) {
		Map<String, Object> modelMap = new HashMap<>();
		// PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
				PersonInfo user = new PersonInfo();
				user.setUserId(1742319L);
		if (orderId != null&&user!=null&&user.getUserId()!=null) {
			OrderExecution orderExecution = null;
			try {
				orderExecution = orderService.dealOrderHadCancel(user,orderId);
				if (orderExecution.getStateInfo() == OrderStateEnum.SUCCESS.getStateInfo()) {
					modelMap.put("success", true);
					return modelMap;
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", orderExecution.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", OrderStateEnum.INNER_ERROR);
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", OrderStateEnum.INFO_NULL);
		}
		return modelMap;
	}

}
