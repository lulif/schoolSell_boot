package com.gdxx.web.frontend;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdxx.dto.AwardExecution;
import com.gdxx.dto.UserAwardMapExecution;
import com.gdxx.dto.UserShopMapExecution;
import com.gdxx.entity.Award;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.Shop;
import com.gdxx.entity.UserAwardMap;
import com.gdxx.entity.UserShopMap;
import com.gdxx.enums.UserAwardMapStateEnum;
import com.gdxx.service.AwardService;
import com.gdxx.service.PersonInfoService;
import com.gdxx.service.ShopService;
import com.gdxx.service.UserAwardMapService;
import com.gdxx.service.UserShopMapService;
import com.gdxx.util.BaiduDwzUtil;
import com.gdxx.util.CodeUtil;
import com.gdxx.util.HttpServletRequestUtil;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

@Controller
@RequestMapping("/frontend")
public class ShopAwardController {
	@Autowired
	private AwardService awardService;
	@Autowired
	private UserShopMapService userShopMapService;
	@Autowired
	private UserAwardMapService userAwardMapService;
	@Autowired
	private PersonInfoService personInfoService;
	@Autowired
	private ShopService shopService;

	@Value("${wechat.prefix}")
	private String urlPrefix;
	@Value("${wechat.middle}")
	private String urlMiddle;
	@Value("${wechat.suffix}")
	private String urlSuffic;
	@Value("${wechat.exchange.url}")
	private String exchangeUrl;

	@SuppressWarnings("unused")
	@RequestMapping(value = "/getawardbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getAwardbyId(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long awardId = HttpServletRequestUtil.getLong(request, "awardId");
//		PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
		//测试数据 发布项目给成上面的
		PersonInfo user=new PersonInfo();
		user.setUserId(1742319L);
		if(user!=null) {
			modelMap.put("needQRCode", true);
		}else {
			modelMap.put("needQRCode", false);
		}
		if (awardId > -1) {
			Award award = awardService.getAwardById(awardId);
			modelMap.put("award", award);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty awardId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/listawardsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listAwardsByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if ((pageIndex > -1) && (pageSize > -1) && (shopId > -1)) {
			String awardName = HttpServletRequestUtil.getString(request, "awardName");
			Award awardCondition = new Award();
			awardCondition.setShopId(shopId);
			awardCondition.setAwardName(awardName);
			AwardExecution ae = awardService.getAwardList(awardCondition, pageIndex, pageSize);

			// PersonInfo personInfo = (PersonInfo)
			// request.getSession().getAttribute("user");
			// UserShopMapExecution usme =
			// userShopMapService.getUserShopMapbyUserAndShop(personInfo.getUserId(),
			// shopId);
			// 测试数据 项目发布要改成上面的
			UserShopMapExecution usme = userShopMapService.getUserShopMapbyUserAndShop(1742319L, shopId);

			modelMap.put("totalPoint", usme.getUserShopMap().getPoint());
			modelMap.put("awardList", ae.getAwardList());
			modelMap.put("count", ae.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/adduserawardmap", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> addUserAwardMap(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long awardId = HttpServletRequestUtil.getLong(request, "awardId");
		// PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		// UserAwardMap userAwardMap = compactUserAwardMapForAdd(user.getUserId(),
		// awardId);
		// 测试数据 项目发布要改成上面的
		UserAwardMap userAwardMap = compactUserAwardMapForAdd(1742319L, awardId);

		if (userAwardMap != null) {
			try {
				UserAwardMapExecution se = userAwardMapService.addUserAwardMap(userAwardMap);
				if (se.getState() == UserAwardMapStateEnum.SUCCESS.getState()) {
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
			modelMap.put("errMsg", "请选择领取的奖品");
		}
		return modelMap;
	}

	@RequestMapping(value = "/listuserawardmapsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserAwardMapsByCustomer(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

		// PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
		// 测试数据 发布项目改成上面的
		PersonInfo user = new PersonInfo();
		user.setUserId(1742319L);

		if ((pageIndex > -1) && (pageSize > -1) && (user.getUserId() != null)) {
			UserAwardMap userAwardMapCondition = new UserAwardMap();
			userAwardMapCondition.setUser(user);
			String awardName = HttpServletRequestUtil.getString(request, "awardName");
			if (awardName != null) {
				Award award = new Award();
				award.setAwardName(awardName);
				userAwardMapCondition.setAward(award);
			}
			UserAwardMapExecution ue = userAwardMapService.listUserAwardMap(userAwardMapCondition, pageIndex, pageSize);

			modelMap.put("userAwardMapList", ue.getUserAwardMapList());
			modelMap.put("count", ue.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or userId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/listusershopmapsbycustomer", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserShopMapsByCustomer(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		Long userId = 1L;
		if ((pageIndex > -1) && (pageSize > -1) && (userId != null)) {
			UserShopMap userShopMapCondition = new UserShopMap();
			// PersonInfo user=(PersonInfo) request.getSession().getAttribute("user");
			// 测试数据 发布项目改成上面的
			PersonInfo user = new PersonInfo();
			user.setUserId(1742319L);

			userShopMapCondition.setUser(user);
			String shopName = HttpServletRequestUtil.getString(request, "shopName");
			if (shopName != null) {
				Shop shop = new Shop();
				shop.setShopName(shopName);
				userShopMapCondition.setShop(shop);
			}
			UserShopMapExecution ue = userShopMapService.listUserShopMap(userShopMapCondition, pageIndex, pageSize);
			modelMap.put("userShopMapList", ue.getUserShopMapList());
			modelMap.put("count", ue.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	private UserAwardMap compactUserAwardMapForAdd(Long userId, Long awardId) {
		UserAwardMap userAwardMap = null;
		if (userId != null && awardId != -1) {
			userAwardMap = new UserAwardMap();
			PersonInfo personInfo = personInfoService.getPersonInfoById(userId);
			Award award = awardService.getAwardById(awardId);
			Shop shop = shopService.getShopByShopId(award.getShopId());
			userAwardMap.setUser(personInfo);
			userAwardMap.setAward(award);
			userAwardMap.setShop(shop);
			userAwardMap.setPoint(award.getPoint());
			userAwardMap.setCreateTime(new Date());
			userAwardMap.setUsedStatus(0);
			userAwardMap.setPoint(award.getPoint());
		}
		return userAwardMap;
	}

	// 生成兑换奖品的二维码
	@RequestMapping(value = "/generateqrcodetoexchange", method = RequestMethod.GET)
	@ResponseBody
	private void generateQRcodeToExchange(HttpServletRequest request, HttpServletResponse response) {
		long awardId = HttpServletRequestUtil.getLong(request, "awardId");
		long userAwardId = HttpServletRequestUtil.getLong(request, "userAwardId");
		// PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
		// 测试数据 项目发布要改成上面的
		PersonInfo user = new PersonInfo();
		user.setUserId(1742319L);
		if (user != null && user.getUserId() != null && awardId != -1) {
			long timeStamp = System.currentTimeMillis();
			String content = "{wwwawardIdwww:" + awardId + ",wwwcustomerIdwww:" + user.getUserId()+ ",wwwuserAwardIdwww:" + userAwardId
					+ ",wwwcreateTimewww:" + timeStamp + "}";
			try {
				String longUrl = urlPrefix + exchangeUrl + urlMiddle + URLEncoder.encode(content, "UTF-8") + urlSuffic;
				String shortUrl = BaiduDwzUtil.createShortUrl(longUrl);
				BitMatrix qRcodeImg = CodeUtil.generateQRCodeStream(shortUrl, response);
				MatrixToImageWriter.writeToStream(qRcodeImg, "png", response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();	
			}
		}
	}

}
