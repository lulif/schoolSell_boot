package com.gdxx.web.shopadmin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdxx.dto.OrderExecution;
import com.gdxx.entity.Shop;
import com.gdxx.enums.OrderStateEnum;
import com.gdxx.service.OrderService;

@Controller
@RequestMapping("/shopadmin")
public class OrderManagentController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/getorderlistbyshop/{orderStatus}", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getOrderListByShop(HttpServletRequest request, @PathVariable Integer orderStatus) {
		Map<String, Object> modelMap = new HashMap<>();
		Shop shop = (Shop) request.getSession().getAttribute("currentShop");
		OrderExecution orderExecution = new OrderExecution();
		if (orderStatus != null && shop != null && shop.getShopId() != null) {
			orderExecution = orderService.getOrderListByIdAndStatus(shop.getShopId(), null, orderStatus);
			if (orderExecution.getStateInfo() == OrderStateEnum.SUCCESS.getStateInfo()) {
				modelMap.put("success", true);
				modelMap.put("orderList", orderExecution.getOrderList());
				modelMap.put("orderProductMap", orderExecution.getOrderIdProductListMap());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", OrderStateEnum.INFO_NULL.getStateInfo());
		}
		modelMap.put("success", false);
		modelMap.put("errMsg", orderExecution.getStateInfo());
		return modelMap;
	}
}
