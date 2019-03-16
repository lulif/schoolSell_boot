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
 * ������ȡ�ѹ�ע��΢�źŵ��û���Ϣ������Ӧ����
 * ��ȡ��ע���ں�֮���΢���û���Ϣ�Ľӿڣ������΢������������
 * ��:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx69b3d902446766d7&redirect_uri=http://232x58c770.iask.in/schoolSell_boot/wechatlogin/logincheck?role_type=1&response_type=code&scope=snsapi_userinfo&state=2#wechat_redirect
 * �����ｫ���ȡ��code(response_type),֮���ٿ���ͨ��code��ȡ��access_token ������ȡ���û���Ϣ
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

	// role_type 1����ȥǰ�� 2ȥ���
	private static final String FRONTEND = "1";
	// private static final String SHOPEND = "2";
	private static Logger log = (Logger) LoggerFactory.getLogger(WechatLoginController.class);

	@RequestMapping(value = "/logincheck", method = { RequestMethod.GET })
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
		// ��ȡ΢�Ź��ںŴ��������code,ͨ��code�ɻ�ȡaccess_token,������ȡ�û���Ϣ
		String code = request.getParameter("code");
		// ���role_type���������������Զ������Ϣ(����ǰ���)
		String roleType = request.getParameter("role_type");
		// �ŵ����ݿ��
		WechatAuth auth = null;
		// ��½��������
		WechatUser user = null;
		String openId = null;
		if (null != code) {
			UserAccessToken token;
			try {
				// ͨ��code��ȡaccess_token
				token = WeiXinUserUtil.getUserAccessToken(code);
				log.debug("weixin login token:" + token.toString());
				// 1.ͨ��access_token��ȡaccessToken
				String accessToken = token.getAccessToken();
				// 2.ͨ��access_token��ȡopenId
				openId = token.getOpenId();
				// ��ͨ��accessToken��openId��ȡ�û��ǳƵ���Ϣ
				user = WeiXinUserUtil.getUserInfo(accessToken, openId);
				log.debug("weixin login user:" + user.toString());
				// ��openId����session��
				request.getSession().setAttribute("openId", openId);
				// ͨ��openId�����ݿ��ȡauth��Ϣ
				auth = wechatAuthService.getWechatAuthByOpenId(openId);
			} catch (IOException e) {
				log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
				e.printStackTrace();
			}
		}
		log.debug("weixin login success.");
		log.debug("login role_type:" + roleType);
		// �����ݿ��ȡ������Ҳ���������ģ��ʹ�΢�ŵ�½������user����ȡauth
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
