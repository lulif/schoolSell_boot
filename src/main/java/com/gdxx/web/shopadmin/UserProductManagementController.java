package com.gdxx.web.shopadmin;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdxx.dto.EchartSeries;
import com.gdxx.dto.EchartXAxis;
import com.gdxx.dto.ShopAuthMapExecution;
import com.gdxx.dto.UserAccessToken;
import com.gdxx.dto.UserProductMapExecution;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.Product;
import com.gdxx.entity.ProductSellDaily;
import com.gdxx.entity.Shop;
import com.gdxx.entity.ShopAuthMap;
import com.gdxx.entity.UserProductMap;
import com.gdxx.entity.WechatAuth;
import com.gdxx.entity.WechatInfo;
import com.gdxx.enums.ShopAuthMapStateEnum;
import com.gdxx.service.ProductSellDailyService;
import com.gdxx.service.ProductService;
import com.gdxx.service.ShopAuthMapService;
import com.gdxx.service.UserProductMapService;
import com.gdxx.service.WechatAuthService;
import com.gdxx.util.HttpServletRequestUtil;
import com.gdxx.util.WeiXinUserUtil;

@Controller
@RequestMapping("/shopadmin")
public class UserProductManagementController {
	@Autowired
	private UserProductMapService userProductMapService;
	@Autowired
	private ProductSellDailyService productSellDailyService;
	@Autowired
	private WechatAuthService wechatAuthService;
	@Autowired
	private ProductService productService;
	@Autowired
	ShopAuthMapService shopAuthMapService;

	@RequestMapping(value = "/listuserproductmapsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserProductMapsByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
			UserProductMap userProductMapCondition = new UserProductMap();
			userProductMapCondition.setShop(currentShop);
			String productName = HttpServletRequestUtil.getString(request, "productName");
			if (productName != null) {
				Product product = new Product();
				product.setProductName(productName);
				userProductMapCondition.setProduct(product);
			}
			UserProductMapExecution ue = userProductMapService.listUserProductMap(userProductMapCondition, pageIndex,
					pageSize);
			modelMap.put("userProductMapList", ue.getUserProductMapList());
			modelMap.put("count", ue.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/listproductselldailyinfobyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductSellDailyInfoByShop(HttpServletRequest request) throws ParseException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		String productName = HttpServletRequestUtil.getString(request, "productName");
		Product product = new Product();
		product.setProductName(productName);
		if (currentShop != null && currentShop.getShopId() != null) {
			ProductSellDaily productSellDailyCondition = new ProductSellDaily();
			productSellDailyCondition.setShop(currentShop);
			productSellDailyCondition.setProduct(product);
			Calendar calendar = Calendar.getInstance();
			/* 测试数据使用，项目发布需去掉这一部分 */
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse("2019-1-28");
			calendar.setTime(date);
			/***********/

			calendar.add(Calendar.DATE, -1);
			Date endTime = calendar.getTime();
			calendar.add(Calendar.DATE, -6);
			Date beginTime = calendar.getTime();
			List<ProductSellDaily> productSellDailyList = productSellDailyService
					.productSellDailyList(productSellDailyCondition, beginTime, endTime);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			// 商品名列表
			HashSet<String> legendData = new HashSet<String>();
			// X轴
			HashSet<String> xData = new HashSet<String>();
			// series
			List<EchartSeries> series = new ArrayList<EchartSeries>();
			// 日销量列表
			List<Integer> totalList = new ArrayList<Integer>();

			// 当前商品名
			String currentProductName = "";
			for (int i = 0; i < productSellDailyList.size(); i++) {
				ProductSellDaily productSellDaily = productSellDailyList.get(i);
				legendData.add(productSellDaily.getProduct().getProductName());
				xData.add(sdf.format(productSellDaily.getCreateTime()));
				if (!currentProductName.equals(productSellDaily.getProduct().getProductName())
						&& !currentProductName.isEmpty()) {
					// productName不等于的才会进入到这里→说明是另一样商品了
					EchartSeries es = new EchartSeries();
					es.setName(currentProductName);
					es.setData(totalList.subList(0, totalList.size()));
					series.add(es);
					totalList = new ArrayList<Integer>();
				}
				totalList.add(productSellDaily.getTotal());
				currentProductName = productSellDaily.getProduct().getProductName();
				// 将最后一个放进去
				if (i == productSellDailyList.size() - 1) {
					EchartSeries es = new EchartSeries();
					es.setName(currentProductName);
					es.setData(totalList.subList(0, totalList.size()));
					series.add(es);
				}
			}
			modelMap.put("series", series);
			modelMap.put("legendData", legendData);
			List<EchartXAxis> xAxis = new ArrayList<EchartXAxis>();
			EchartXAxis exa = new EchartXAxis();
			exa.setData(xData);
			xAxis.add(exa);
			modelMap.put("xAxis", xAxis);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("success", "empty shopId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/adduserproductmap", method = RequestMethod.GET)
	private String addUserProductmap(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
				System.out.println("Fail 1");
				return "/shop/qRcodeOperationFail";
			}
			// 验证二维码是否过期
			if (!checkQRcodeInfo(wechatInfo)) {
				System.out.println("Fail 2");
				return "/shop/qRcodeOperationFail";
			}

			try {
				Long productId = wechatInfo.getProductId();
				Long customerId = wechatInfo.getCustomerId();
				UserProductMap userProductMap = compactUserProductMapForAdd(customerId, productId,
						auth.getPersonInfo());
				// 验证 确认购买 操作员权限
				if (userProductMap != null && customerId != -1) {
					if (!checkShopAuth(operator.getUserId(), userProductMap)) {
						System.out.println("Fail 3");
						return "/shop/qRcodeOperationFail";
					}
				}
				UserProductMapExecution se = null;
				String succ = (String) request.getSession().getServletContext().getAttribute("succ");
				if (succ == null) {
					se = userProductMapService.addUserProductMap(userProductMap);
				}
				if (se.getState() == ShopAuthMapStateEnum.SUCCESS.getState() || succ != null) {
					System.out.println("Success good");
					request.getSession().getServletContext().setAttribute("succ", "ok");
					return "/shop/qRcodeOperationSuccess";
				} else {
					System.out.println("Fail 4");
					return "/shop/qRcodeOperationFail";
				}
			} catch (Exception e) {
				System.out.println("Fail 5");
				return "/shop/qRcodeOperationFail";
			}
		} else {
			System.out.println("Fail 6");
			return "/shop/qRcodeOperationFail";
		}

	}

	private boolean checkShopAuth(Long userId, UserProductMap userProductMap) {
		ShopAuthMapExecution shopAuthExecution = shopAuthMapService
				.listShopAuthMapByShopId(userProductMap.getShop().getShopId(), 1, 999);
		for (ShopAuthMap shopAuthMap : shopAuthExecution.getShopAuthMapList()) {
			if ((long) shopAuthMap.getEmployee().getUserId() == (long) userId) {
				return true;
			}
		}
		return false;
	}

	private UserProductMap compactUserProductMapForAdd(Long customerId, Long productId, PersonInfo operator) {
		UserProductMap userProductMap = new UserProductMap();
		if (customerId != null && productId != null) {
			PersonInfo customer = new PersonInfo();
			customer.setUserId(customerId);

			Product product = productService.getProductById(productId);
			userProductMap.setProduct(product);
			userProductMap.setShop(product.getShop());
			userProductMap.setPoint(product.getPoint());
			userProductMap.setUser(customer);
			userProductMap.setCreateTime(new Date());
			userProductMap.setOperator(operator);
		}
		return userProductMap;
	}

	private boolean checkQRcodeInfo(WechatInfo wechatInfo) {
		if (wechatInfo != null && wechatInfo.getProductId() != null && wechatInfo.getCreateTime() != null) {
			long nowTime = System.currentTimeMillis();
			if ((nowTime - wechatInfo.getCreateTime()) <= 300000) {
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
}
