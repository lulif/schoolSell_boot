package com.gdxx.web.wechat;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import me.chanjar.weixin.mp.api.WxMpService;

@Controller
@RequestMapping("/wechat")
public class WechatController {

	@Autowired
	private WxMpService wxMpService;

	@RequestMapping(method = { RequestMethod.GET })
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		// 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		PrintWriter out = null;
		try {
			out = response.getWriter();
			// if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			if (wxMpService.checkSignature(timestamp, nonce, signature)) {
				// 成功則原样返回echostr
				out.print(echostr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}
	}
}
