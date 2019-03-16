package com.gdxx.web.wechat;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdxx.dto.ShopExecution;
import com.gdxx.dto.UserAccessToken;
import com.gdxx.dto.WechatAuthExecution;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.WechatAuth;
import com.gdxx.entity.WechatUser;
import com.gdxx.enums.WechatAuthStateEnum;
import com.gdxx.service.PersonInfoService;
import com.gdxx.service.ShopService;
import com.gdxx.service.WechatAuthService;
import com.gdxx.util.HttpServletRequestUtil;
import com.gdxx.util.WeiXinUserUtil;

import ch.qos.logback.classic.Logger;

/*
 * 用来获取已关注此微信号的用户信息并做相应处理
 * 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
 * 例:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx69b3d902446766d7&redirect_uri=http://232x58c770.iask.in/schoolSell_boot/wechatlogin/logincheck?role_type=1&response_type=code&scope=snsapi_userinfo&state=2#wechat_redirect
 * 则这里将会获取到code(response_type),之后再可以通过code获取到access_token 进而获取到用户信息
 */
@Controller
@RequestMapping("/wechatlogin")
public class WechatLoginController {
	@Autowired
	private WechatAuthService wechatAuthService;
	@Autowired
	private PersonInfoService personInfoService;
	// @Autowired
	// private ShopService shopService;

	// role_type 1代表去前端 2去后端
	private static final String FRONTEND = "1";
	// private static final String SHOPEND = "2";
	private static Logger log = (Logger) LoggerFactory.getLogger(WechatLoginController.class);

	@RequestMapping(value = "/logincheck", method = { RequestMethod.GET })
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
		// 获取微信公众号传输过来的code,通过code可获取access_token,进而获取用户信息
		String code = request.getParameter("code");
		// 这个role_type可以用来传我们自定义的信息(区分前后端)
		String roleType = request.getParameter("role_type");
		// 放到数据库的
		WechatAuth auth = null;
		// 登陆传过来的
		WechatUser user = null;
		String openId = null;
		if (null != code) {
			UserAccessToken token;
			try {
				// 通过code获取access_token
				token = WeiXinUserUtil.getUserAccessToken(code);
				log.debug("weixin login token:" + token.toString());
				// 1.通过access_token获取accessToken
				String accessToken = token.getAccessToken();
				// 2.通过access_token获取openId
				openId = token.getOpenId();
				// →通过accessToken和openId获取用户昵称等信息
				user = WeiXinUserUtil.getUserInfo(accessToken, openId);
				log.debug("weixin login user:" + user.toString());
				// 把openId塞到session中
				request.getSession().setAttribute("openId", openId);
				// 通过openId从数据库获取auth信息
				auth = wechatAuthService.getWechatAuthByOpenId(openId);
			} catch (IOException e) {
				log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
				e.printStackTrace();
			}
		}
		log.debug("weixin login success.");
		log.debug("login role_type:" + roleType);
		// 从数据库获取不到，也就是新来的，就从微信登陆过来的user中提取auth
		PersonInfo personInfo = null;
		if (auth == null) {
			personInfo = WeiXinUserUtil.getPersonInfoFromRequest(user);
			auth = new WechatAuth();
			auth.setOpenId(openId);
			if (FRONTEND.equals(roleType)) {
				personInfo.setUserType(1);
			} else {
				personInfo.setUserType(2);
			}
			auth.setPersonInfo(personInfo);
			WechatAuthExecution we = wechatAuthService.register(auth, null);
			if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
				return null;
			}
		}
		personInfo = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
		request.getSession().setAttribute("user", personInfo);

		if (FRONTEND.equals(roleType)) {
			return "/frontend/index";
		} else {
			return "/shop/shopList";
		}

	}
}
