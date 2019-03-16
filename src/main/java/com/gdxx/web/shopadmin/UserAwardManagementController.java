package com.gdxx.web.shopadmin;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdxx.dto.ShopAuthMapExecution;
import com.gdxx.dto.UserAccessToken;
import com.gdxx.dto.UserAwardMapExecution;
import com.gdxx.dto.UserProductMapExecution;
import com.gdxx.entity.Award;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.Product;
import com.gdxx.entity.Shop;
import com.gdxx.entity.ShopAuthMap;
import com.gdxx.entity.UserAwardMap;
import com.gdxx.entity.UserProductMap;
import com.gdxx.entity.WechatAuth;
import com.gdxx.entity.WechatInfo;
import com.gdxx.enums.ShopAuthMapStateEnum;
import com.gdxx.service.AwardService;
import com.gdxx.service.ProductService;
import com.gdxx.service.ShopAuthMapService;
import com.gdxx.service.UserAwardMapService;
import com.gdxx.service.WechatAuthService;
import com.gdxx.util.HttpServletRequestUtil;
import com.gdxx.util.WeiXinUserUtil;

@Controller
@RequestMapping("/shopadmin")
public class UserAwardManagementController {
	@Autowired
	private UserAwardMapService userAwardMapService;
	@Autowired
	private WechatAuthService wechatAuthService;
	@Autowired
	private AwardService awardService;
	@Autowired
	ShopAuthMapService shopAuthMapService;

	@RequestMapping(value = "/listuserawardmapsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserAwardMapsByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
			UserAwardMap userAwardMap = new UserAwardMap();
			userAwardMap.setShop(currentShop);
			String awardName = HttpServletRequestUtil.getString(request, "awardName");
			if (awardName != null) {
				Award award = new Award();
				award.setAwardName(awardName);
				userAwardMap.setAward(award);
			}
			UserAwardMapExecution ue = userAwardMapService.listUserAwardMap(userAwardMap, pageIndex, pageSize);
			modelMap.put("userAwardMapList", ue.getUserAwardMapList());
			modelMap.put("count", ue.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/exchangeaward", method = RequestMethod.GET)
	private String ExchangeAward(HttpServletRequest request, HttpServletResponse response) throws IOException {
		WechatAuth auth = getOperatorInfo(request);
		if (auth != null) {
			PersonInfo operator = auth.getPersonInfo();
			String qRcodeInfo = new String(
					URLDecoder.decode(HttpServletRequestUtil.getString(request, "state"), "UTF-8"));
			ObjectMapper mapper = new ObjectMapper();
			WechatInfo wechatInfo = null;
			try {
				wechatInfo = mapper.readValue(qRcodeInfo.replace("www", "\""), WechatInfo.class);
			} catch (Exception e) {
				return "/shop/qRcodeOperationFail";
			}
			// 验证二维码是否过期
			if (!checkQRcodeInfo(wechatInfo)) {
				return "/shop/qRcodeOperationFail";
			}

			try {
				Long awardId = wechatInfo.getAwardId();
				Long customerId = wechatInfo.getCustomerId();
				Long userAwardId = wechatInfo.getUserAwardId();
				UserAwardMap userAwardMap = compactUserAwardMapForExchange(customerId, awardId, auth.getPersonInfo(),
						userAwardId);
				// 验证 确认购买 操作员权限
				if (userAwardMap != null && customerId != -1) {
					if (!checkShopAuth(operator.getUserId(), userAwardMap)) {
						return "/shop/qRcodeOperationFail";
					}
				}

				UserAwardMapExecution ue1 = userAwardMapService.modifyUserAwardMap(userAwardMap);
				String succ = (String) request.getSession().getServletContext().getAttribute("succ");
				if (ue1.getState() == ShopAuthMapStateEnum.SUCCESS.getState() || succ != null) {
					request.getSession().getServletContext().setAttribute("succ", "ok");
					System.out.println("you → success");
					return "/shop/qRcodeOperationSuccess";
				} else {
					return "/shop/qRcodeOperationFail";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "/shop/qRcodeOperationFail";
			}

		} else {
			return "/shop/qRcodeOperationFail";
		}

	}

	private UserAwardMap compactUserAwardMapForExchange(Long customerId, Long awardId, PersonInfo operator,
			Long userAwardId) {
		UserAwardMap userAwardMap = new UserAwardMap();
		if (customerId != null && awardId != null && operator != null && userAwardId != null) {
			PersonInfo customer = new PersonInfo();
			customer.setUserId(customerId);

			Award award = awardService.getAwardById(awardId);
			userAwardMap.setAward(award);
			Shop shop = new Shop();
			shop.setShopId(award.getShopId());
			userAwardMap.setShop(shop);
			userAwardMap.setPoint(award.getPoint());
			userAwardMap.setUser(customer);
			userAwardMap.setCreateTime(new Date());
			userAwardMap.setOperator(operator);
			userAwardMap.setUsedStatus(1);
			userAwardMap.setCreateTime(new Date());
			userAwardMap.setUserAwardId(userAwardId);
		}
		return userAwardMap;

	}

	private boolean checkShopAuth(Long userId, UserAwardMap userAwardMap) {
		ShopAuthMapExecution shopAuthExecution = shopAuthMapService
				.listShopAuthMapByShopId(userAwardMap.getShop().getShopId(), 1, 999);
		for (ShopAuthMap shopAuthMap : shopAuthExecution.getShopAuthMapList()) {
			if ((long) shopAuthMap.getEmployee().getUserId() == (long) userId) {
				return true;
			}
		}
		return false;
	}

	private WechatAuth getOperatorInfo(HttpServletRequest request) {
		String code = request.getParameter("code");
		WechatAuth auth = null;
		if (null != code) {
			try {
				String openId = (String) request.getSession().getServletContext().getAttribute("openId");
				if (openId == null) {
					UserAccessToken token = WeiXinUserUtil.getUserAccessToken(code);
					openId = token.getOpenId();
					request.getSession().getServletContext().setAttribute("openId", openId);
				}
				auth = wechatAuthService.getWechatAuthByOpenId(openId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return auth;
	}

	private boolean checkQRcodeInfo(WechatInfo wechatInfo) {
		if (wechatInfo != null && wechatInfo.getAwardId() != null && wechatInfo.getCreateTime() != null) {
			long nowTime = System.currentTimeMillis();
			if ((nowTime - wechatInfo.getCreateTime()) <= 300000) {
				return true;
			}
		}
		return false;
	}
}
