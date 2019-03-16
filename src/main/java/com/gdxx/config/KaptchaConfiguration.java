package com.gdxx.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.servlet.KaptchaServlet;

@Configuration
public class KaptchaConfiguration {
	@Value("${kaptcha.border}")
	private String border;
	@Value("${kaptcha.textproducer.font.color}")
	private String textproducerFontColor;
	@Value("${kaptcha.image.width}")
	private String imageWidth;
	@Value("${kaptcha.textproducer.char.string}")
	private String textproducerCharString;
	@Value("${kaptcha.image.height}")
	private String imageHeight;
	@Value("${kaptcha.textproducer.font.size}")
	private String textproducerFontSize;
	@Value("${kaptcha.noise.color}")
	private String noiseColor;
	@Value("${kaptcha.textproducer.char.length}")
	private String textproducerCharLength;
	@Value("${kaptcha.textproducer.font.names}")
	private String textproducerFontNames;

	@Bean
	public ServletRegistrationBean<KaptchaServlet> servletRegistrationBean() {
		ServletRegistrationBean<KaptchaServlet> servlet = new ServletRegistrationBean<KaptchaServlet>(
				new KaptchaServlet(), "/Kaptcha");
		servlet.addInitParameter("kaptcha.border", border);
		servlet.addInitParameter("kaptcha.textproducer.font.color", textproducerFontColor);
		servlet.addInitParameter("kaptcha.image.width", imageWidth);
		servlet.addInitParameter("kaptcha.textproducer.char.string", textproducerCharString);
		servlet.addInitParameter("kaptcha.image.height", imageHeight);
		servlet.addInitParameter("kaptcha.textproducer.font.size", textproducerFontSize);
		servlet.addInitParameter("kaptcha.noise.color", noiseColor);
		servlet.addInitParameter("kaptcha.textproducer.char.length", textproducerCharLength);
		servlet.addInitParameter("kaptcha.textproducer.font.names", textproducerFontNames);
		return servlet;

	}

}
