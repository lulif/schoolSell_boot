package com.gdxx.web.frontend;

import java.net.URLEncoder;
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

import com.gdxx.dto.UserProductMapExecution;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.Product;
import com.gdxx.entity.UserProductMap;
import com.gdxx.service.ProductService;
import com.gdxx.service.UserProductMapService;
import com.gdxx.util.BaiduDwzUtil;
import com.gdxx.util.CodeUtil;
import com.gdxx.util.HttpServletRequestUtil;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

@Controller
@RequestMapping("/frontend")
public class ProductDetailController {
	@Autowired
	private ProductService productService;
	@Autowired
	private UserProductMapService userProductMapService;

	@Value("${wechat.prefix}")
	private String urlPrefix;
	@Value("${wechat.middle}")
	private String urlMiddle;
	@Value("${wechat.suffix}")
	private String urlSuffic;
	@Value("${wechat.product.url}")
	private String productUrl;

	@SuppressWarnings("unused")
	@RequestMapping(value = "/productdetailpageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> productDetailPageInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long productId = HttpServletRequestUtil.getLong(request, "productid");

		Product product = null;
		// PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
		// 测试数据 项目发布要改成上面的
		PersonInfo user = new PersonInfo();
		user.setUserId(1742319L);

		if (user != null) {
			modelMap.put("needQRCode", true);
		} else {
			modelMap.put("needQRCode", false);
		}
		if (productId != -1) {
			product = productService.getProductById(productId);
			modelMap.put("product", product);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty productId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/listuserproductmapsbycustomer", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserProductMapsByCustomer(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		// PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
		// 测试数据 项目发布要改成上面的
		PersonInfo user = new PersonInfo();
		user.setUserId(1742319L);
		
		if ((pageIndex > -1) && (pageSize > -1) && (user != null) && (user.getUserId() != -1)) {
			UserProductMap userProductMapCondition = new UserProductMap();
			userProductMapCondition.setUser(user);
			String productName = HttpServletRequestUtil.getString(request, "productName");
			if (productName != null) {
				Product p=new Product();
				userProductMapCondition.setProduct(p);
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

	// 生成购买商品的二维码
	@RequestMapping(value = "/generateqrcodetobuy", method = RequestMethod.GET)
	@ResponseBody
	private void generateQRcodeToBuy(HttpServletRequest request, HttpServletResponse response) {
		long productId = HttpServletRequestUtil.getLong(request, "productId");
		// PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
		// 测试数据 项目发布要改成上面的
		PersonInfo user = new PersonInfo();
		user.setUserId(1742319L);
		if (user != null && user.getUserId() != null && productId != -1) {
			long timeStamp = System.currentTimeMillis();
			String content = "{wwwproductIdwww:" + productId + ",wwwcustomerIdwww:" + user.getUserId()
					+ ",wwwcreateTimewww:" + timeStamp + "}";
			try {
				String longUrl = urlPrefix + productUrl + urlMiddle + URLEncoder.encode(content, "UTF-8") + urlSuffic;
				String shortUrl = BaiduDwzUtil.createShortUrl(longUrl);
				BitMatrix qRcodeImg = CodeUtil.generateQRCodeStream(shortUrl, response);
				MatrixToImageWriter.writeToStream(qRcodeImg, "png", response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
