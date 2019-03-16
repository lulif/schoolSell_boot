package com.gdxx.web.local;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdxx.dto.LocalAuthExecution;
import com.gdxx.entity.LocalAuth;
import com.gdxx.entity.PersonInfo;
import com.gdxx.enums.LocalAuthStateEnum;
import com.gdxx.service.LocalAuthService;
import com.gdxx.util.CodeUtil;
import com.gdxx.util.HttpServletRequestUtil;
import com.gdxx.util.MD5;

@Controller
@RequestMapping("/local")
public class LocalAuthController {
	@Autowired
	private LocalAuthService localAuthService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> Register(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		LocalAuth localAuth = null;
		String localAuthStr = HttpServletRequestUtil.getString(request, "localAuthStr");
		try {
			localAuth = mapper.readValue(localAuthStr, LocalAuth.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (localAuth != null && localAuth.getPassword() != null && localAuth.getUserName() != null) {
			try {
				PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
				if (user != null && localAuth.getPersonInfo() != null) {
					localAuth.getPersonInfo().setUserId(user.getUserId());
				}
				LocalAuthExecution le = localAuthService.register(localAuth);
				if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", le.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入注册信息");
		}
		return modelMap;
	}

	@RequestMapping(value = "/bindlocalauth", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> bindLocalAuth(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入有误！");
			return modelMap;
		}
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		if (userName != null && password != null && user.getUserId() != null) {
			LocalAuth localAuth = new LocalAuth();
			localAuth.setUserName(userName);
			localAuth.setPassword(password);
			localAuth.setPersonInfo(user);

			LocalAuthExecution le = localAuthService.bindLocalAuth(localAuth);
			if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", le.getStateInfo());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码均不能为空");
		}
		return modelMap;
	}

	@RequestMapping(value = "/changelocalpassword", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> changeLocalPassword(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入有误！");
			return modelMap;
		}
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		if (userName != null && password != null && newPassword != null && user.getUserId() != null) {
			if (password.equals(newPassword)) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "新/旧密码需不同");
			}
			try {
				LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
				if (localAuth == null || !localAuth.getUserName().equals(userName)) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "输入的账号非本次登陆账号");
				}
				LocalAuthExecution le = localAuthService.modifyLocalAuth(user.getUserId(), userName, password,
						newPassword);
				if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", le.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		}
		return modelMap;
	}

	@RequestMapping(value = "/logincheck", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> loginCheck(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
		if (needVerify && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入错误的验证码");
		}
		int userType = HttpServletRequestUtil.getInt(request, "userType");
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = MD5.getMd5(HttpServletRequestUtil.getString(request, "password"));
		if (userName != null && password != null) {
			LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(userName, password);
			if (localAuth != null) {
				if (localAuth.getPersonInfo().getUserType()!=userType) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "用户类型不匹配");
					return modelMap;
				}
				if (localAuth.getPersonInfo().getEnableStatus() != 1) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "此用户已无效");
					return modelMap;
				}
				modelMap.put("success", true);
				request.getSession().setAttribute("user", localAuth.getPersonInfo());
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码错误");
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码均不能为空");
		}
		return modelMap;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> logout(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		request.getSession().setAttribute("user", null);
		modelMap.put("success", true);
		return modelMap;
	}
}
