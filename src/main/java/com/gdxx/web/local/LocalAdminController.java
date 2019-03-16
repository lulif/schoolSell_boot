package com.gdxx.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/local")
public class LocalAdminController {
	@RequestMapping(value = "/gotobind", method = RequestMethod.GET)
	private String gotoBind() {
		return "/local/accountBind";
	}

	@RequestMapping(value = "/changepassword", method = RequestMethod.GET)
	private String changePassword() {
		return "/local/changePsw";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	private String login() {
		return "/local/login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	private String register() {
		return "/local/register";
	}
}
