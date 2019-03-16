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
		// ΢�ż���ǩ����signature����˿�������д��token�����������е�timestamp������nonce������
		String signature = request.getParameter("signature");
		// ʱ���
		String timestamp = request.getParameter("timestamp");
		// �����
		String nonce = request.getParameter("nonce");
		// ����ַ���
		String echostr = request.getParameter("echostr");

		// ͨ������signature���������У�飬��У��ɹ���ԭ������echostr����ʾ����ɹ����������ʧ��
		PrintWriter out = null;
		try {
			out = response.getWriter();
			// if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			if (wxMpService.checkSignature(timestamp, nonce, signature)) {
				// �ɹ��tԭ������echostr
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
