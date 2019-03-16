package com.gdxx.web.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdxx.dto.ReceivingAddressExecution;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.ReceivingAddress;
import com.gdxx.enums.ReceivingAddressStateEnum;
import com.gdxx.service.ReceivingAddressService;
import com.gdxx.util.CodeUtil;
import com.gdxx.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ReceivingAddressCntroller {

	@Autowired
	private ReceivingAddressService receivingAddressService;

	@RequestMapping(value = "/insertaddress", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> insertAddress(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入有误！");
			return modelMap;
		}
		String receAddressMsg = HttpServletRequestUtil.getString(request, "receivingAddress");
		ObjectMapper mapper = new ObjectMapper();
		ReceivingAddress receivingAddress = null;
		try {
			receivingAddress = mapper.readValue(receAddressMsg, ReceivingAddress.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		// PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
		PersonInfo user = new PersonInfo();
		user.setUserId(1742319L);
		receivingAddress.setUser(user);
		if (receivingAddress != null && receivingAddress.getUser() != null
				&& !receivingAddress.getContactName().equals("") && !receivingAddress.getContactPhone().equals("")
				&& !receivingAddress.getLocation().equals("") && !receivingAddress.getLocationDetails().equals("")) {
			ReceivingAddressExecution rae = receivingAddressService.addReceivingAddress(receivingAddress);
			if (rae.getStateInfo() == ReceivingAddressStateEnum.SUCCESS.getStateInfo()) {
				modelMap.put("success", true);
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请完整填写地址信息");
			return modelMap;
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyaddressinfo", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyAddressInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入有误！");
			return modelMap;
		}
		String receAddressMsg = HttpServletRequestUtil.getString(request, "receivingAddress");
		ObjectMapper mapper = new ObjectMapper();
		ReceivingAddress receivingAddress = null;
		try {
			receivingAddress = mapper.readValue(receAddressMsg, ReceivingAddress.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		// PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
		PersonInfo user = new PersonInfo();
		user.setUserId(1742319L);
		receivingAddress.setUser(user);
		if (receivingAddress.getAddressId() != null && receivingAddress != null && receivingAddress.getUser() != null
				&& !receivingAddress.getContactName().equals("") && !receivingAddress.getContactPhone().equals("")
				&& !receivingAddress.getLocation().equals("") && !receivingAddress.getLocationDetails().equals("")) {
			ReceivingAddressExecution rae = receivingAddressService.modifyReceivingAddress(receivingAddress);
			if (rae.getStateInfo() == ReceivingAddressStateEnum.SUCCESS.getStateInfo()) {
				modelMap.put("success", true);
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请完整填写地址信息");
			return modelMap;
		}
		return modelMap;
	}

	@RequestMapping(value = "/listaddress/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listReceivingAddress(HttpServletRequest request,
			@PathVariable("pageIndex") Integer pageIndex, @PathVariable("pageSize") Integer pageSize) {
		Map<String, Object> modelMap = new HashMap<>();
		// PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		PersonInfo user = new PersonInfo();
		user.setUserId(1742319L);
		if (user != null && user.getUserId() != null && pageIndex != null & pageSize != null) {
			ReceivingAddressExecution rae = receivingAddressService.getReceivingAddresList(user.getUserId(), pageIndex,
					pageSize);
			if (rae.getStateInfo() == ReceivingAddressStateEnum.SUCCESS.getStateInfo()) {
				modelMap.put("success", true);
				modelMap.put("receAddressList", rae.getReceivingAddressList());
				modelMap.put("count", rae.getCount());
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "获取收获地址时发生错误");
			}
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifytodefault", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyToDeafult(@RequestParam("addressId") Long addressId) {
		Map<String, Object> modelMap = new HashMap<>();
		if (addressId != null) {
			try {
				// PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
				PersonInfo user = new PersonInfo();
				user.setUserId(1742319L);
				ReceivingAddressExecution rae = receivingAddressService.updateDefaultAddress(addressId,
						user.getUserId());
				if (rae.getStateInfo() == ReceivingAddressStateEnum.SUCCESS.getStateInfo()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMSg", rae.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}
		return modelMap;
	}

	@RequestMapping(value = "/getreceivingaddressinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getReceivingAddressInfo(@RequestParam("addressId") Long addressId) {
		Map<String, Object> modelMap = new HashMap<>();
		if (addressId != null) {
			ReceivingAddressExecution addressExecution = receivingAddressService.getReceivingAddressById(addressId);
			if (addressExecution.getStateInfo() == ReceivingAddressStateEnum.SUCCESS.getStateInfo()) {
				modelMap.put("success", true);
				modelMap.put("receivingAddress", addressExecution.getReceivingAddress());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "addressId为空");
		}
		return modelMap;
	}

	@RequestMapping(value = "/removeaddress", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> removeReceivingAddress(@RequestParam("addressId") Long addressId) {
		Map<String, Object> modelMap = new HashMap<>();
		if (addressId != null) {
			try {
				// PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
				PersonInfo user = new PersonInfo();
				user.setUserId(1742319L);
				if (user != null) {
					ReceivingAddressExecution rae = receivingAddressService.removeReceivingAddress(addressId,
							user.getUserId());
					if (rae.getStateInfo() == ReceivingAddressStateEnum.SUCCESS.getStateInfo()) {
						modelMap.put("success", true);
						return modelMap;
					}
				}
			} catch (Exception e) {
				modelMap.put("succes", false);
				modelMap.put("errMsg", e.getMessage());
			}
		} else {
			modelMap.put("success", false);
		}
		return modelMap;
	}

	@RequestMapping(value = "/getdefaultaddress", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getDefaultAddress() {
		Map<String, Object> modelMap = new HashMap<>();
		// PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		PersonInfo user = new PersonInfo();
		user.setUserId(1742319L);
		if (user != null) {
			ReceivingAddressExecution rae = receivingAddressService.getDefaultReceivingAddress(user.getUserId());
			if (rae.getStateInfo() == ReceivingAddressStateEnum.SUCCESS.getStateInfo()) {
				modelMap.put("defaultAddress", rae.getReceivingAddress());
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "请设置默认地址");
			}

		}
		return modelMap;
	}

}
