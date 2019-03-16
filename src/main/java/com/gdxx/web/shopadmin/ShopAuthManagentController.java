package com.gdxx.web.shopadmin;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdxx.dto.ShopAuthMapExecution;
import com.gdxx.dto.UserAccessToken;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.Shop;
import com.gdxx.entity.ShopAuthMap;
import com.gdxx.entity.WechatAuth;
import com.gdxx.entity.WechatInfo;
import com.gdxx.enums.ShopAuthMapStateEnum;
import com.gdxx.service.PersonInfoService;
import com.gdxx.service.ShopAuthMapService;
import com.gdxx.service.WechatAuthService;
import com.gdxx.util.BaiduDwzUtil;
import com.gdxx.util.CodeUtil;
import com.gdxx.util.HttpServletRequestUtil;
import com.gdxx.util.WeiXinUserUtil;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

@Controller
@RequestMapping("/shopadmin")
public class ShopAuthManagentController {
	@Autowired
	private ShopAuthMapService shopAuthMapService;
	@Autowired
	private PersonInfoService personInfoService;
	@Autowired
	private WechatAuthService wechatAuthService;

	@Value("${wechat.prefix}")
	private String urlPrefix;
	@Value("${wechat.middle}")
	private String urlMiddle;
	@Value("${wechat.suffix}")
	private String urlSuffic;
	@Value("${wechat.auth.url}")
	private String authUrl;

	@RequestMapping(value = "/listshopauthmapsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopAuthMapsByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
			ShopAuthMapExecution se = shopAuthMapService.listShopAuthMapByShopId(currentShop.getShopId(), pageIndex,
					pageSize);
			modelMap.put("shopAuthMapList", se.getShopAuthMapList());
			modelMap.put("count", se.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/getshopauthmapbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopAuthMapById(@RequestParam Long shopAuthId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (shopAuthId != null && shopAuthId > -1) {
			ShopAuthMap shopAuthMap = shopAuthMapService.getShopAuthMapById(shopAuthId);
			modelMap.put("shopAuthMap", shopAuthMap);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopAuthId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyshopauthmap", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShopAuthMap(String shopAuthMapStr, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 判斷是否是编辑(需要验证码),删除不需要验证码
		// 按删除也是跳到这个Controller(不能真正删除ShopAuthMap的记录，它还和其他表的记录有关系)
		boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
		if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		ShopAuthMap shopAuthMap = null;
		try {
			shopAuthMap = mapper.readValue(shopAuthMapStr, ShopAuthMap.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (shopAuthMap != null && shopAuthMap.getShopAuthId() != null) {
			try {
				if (!checkPermission(shopAuthMap.getShopAuthId())) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "无法对店家本身权限做操作(已是店铺最高权限)");
				}
				ShopAuthMapExecution se = shopAuthMapService.modifyShopAuthMap(shopAuthMap);
				if (se.getState() == ShopAuthMapStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入要修改的授权信息");
		}
		return modelMap;
	}

	private boolean checkPermission(Long shopAuthId) {
		ShopAuthMap shopAuthMap = shopAuthMapService.getShopAuthMapById(shopAuthId);
		if (shopAuthMap.getTitleFlag() == 0) {
			// 店家本身，不能操作
			return false;
		}
		return true;
	}

	/*
	 * 生成带有url的二维码，微信扫一扫就能链接到对应的url
	 */
	@RequestMapping(value = "/generateqrcode", method = RequestMethod.GET)
	@ResponseBody
	private void generateQRcode(HttpServletRequest request, HttpServletResponse response) {
		Shop shop = (Shop) request.getSession().getAttribute("currentShop");
		if (shop != null && shop.getShopId() != null) {
			long timeStamp = System.currentTimeMillis();
			String content = "{wwwshopIdwww:" + shop.getShopId() + ",wwwcreateTimewww:" + timeStamp + "}";
			try {
				String longUrl = urlPrefix + authUrl + urlMiddle + URLEncoder.encode(content, "UTF-8") + urlSuffic;
				String shortUrl = BaiduDwzUtil.createShortUrl(longUrl);
				BitMatrix qRcodeImg = CodeUtil.generateQRCodeStream(shortUrl, response);
				MatrixToImageWriter.writeToStream(qRcodeImg, "png", response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 扫二维码后到这个链接生成授权信息
	 */
	@RequestMapping(value = "/addshopauthmap", method = RequestMethod.GET)
	private String addShopAuthMap(HttpServletRequest request, HttpServletResponse response) throws IOException {
		WechatAuth auth = getEmployeeInfo(request);
		if (auth != null) {
			PersonInfo employee = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
			request.getSession().setAttribute("user", employee);
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
			// 去重校验
			ShopAuthMapExecution allMapList = shopAuthMapService.listShopAuthMapByShopId(wechatInfo.getShopId(), 0,
					999);
			List<ShopAuthMap> ShopAuthList = allMapList.getShopAuthMapList();
			for (ShopAuthMap sam : ShopAuthList) {
				if (sam.getEmployee().getUserId() == employee.getUserId()) {
					return "/shop/qRcodeOperationFail";
				}
			}

			try {
				ShopAuthMap shopAuthMap = new ShopAuthMap();
				Shop shop = new Shop();
				shop.setShopId(wechatInfo.getShopId());
				shopAuthMap.setShop(shop);
				shopAuthMap.setEmployee(employee);
				shopAuthMap.setTitle("员工");
				shopAuthMap.setTitleFlag(1);
				ShopAuthMapExecution same = shopAuthMapService.addShopAuthMap(shopAuthMap);
				String succ = (String) request.getSession().getServletContext().getAttribute("succ");
				if (same.getState() == ShopAuthMapStateEnum.SUCCESS.getState() || succ != null) {
					request.getSession().getServletContext().setAttribute("succ", "ok");
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

	private boolean checkQRcodeInfo(WechatInfo wechatInfo) {
		if (wechatInfo != null && wechatInfo.getShopId() != null && wechatInfo.getCreateTime() != null) {
			long nowTime = System.currentTimeMillis();
			if ((nowTime - wechatInfo.getCreateTime()) <= 300000) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private WechatAuth getEmployeeInfo(HttpServletRequest request) throws IOException {
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
}
