package com.gdxx.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.gdxx.interceptor.shopadmin.ShopInterceptor;
import com.gdxx.interceptor.shopadmin.ShopPermissionInterceptor;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc // �൱��<mvc:annotation-driven />
public class MvcConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/*
	 * ��̬��Դ������
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// windows
		registry.addResourceHandler("/upload/**").addResourceLocations(
				"file:C:\\Program Files\\apache-tomcat-9.0.7\\webapps\\schoolSellPhoto\\upload\\");
		// linux
		// registry.addResourceHandler("/upload/**").addResourceLocations(
		// "file:/root/apache-tomcat-8.5.37/webapps/schoolSellPhoto/upload/");
	}

	/*
	 * 静态资源
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/*
	 * ����viewResolver
	 */
	@Bean(name = "viewResolver")
	public InternalResourceViewResolver createInternalResourceViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setApplicationContext(applicationContext);
		viewResolver.setCache(false);
		viewResolver.setPrefix("/WEB-INF/html");
		viewResolver.setSuffix(".html");
		return viewResolver;
	}

	/*
	 * 文件上传
	 */
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver createCommonsMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("utf-8");
		multipartResolver.setMaxInMemorySize(20971520);
		multipartResolver.setMaxInMemorySize(20971520);
		return multipartResolver;
	}

	/*
	 * ����������
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		String interceptPath = "/shopadmin/**";

		InterceptorRegistration loginIR = registry.addInterceptor(new ShopInterceptor());
		loginIR.addPathPatterns(interceptPath);
		// 扫码操作的路由
		loginIR.excludePathPatterns("/shopadmin/addshopauthmap");
		loginIR.excludePathPatterns("/shopadmin/adduserproductmap");
		loginIR.excludePathPatterns("/shopadmin/exchangeaward");

		InterceptorRegistration permissionIR = registry.addInterceptor(new ShopPermissionInterceptor());
		permissionIR.addPathPatterns(interceptPath);

		permissionIR.excludePathPatterns("/shopadmin/shoplist");
		permissionIR.excludePathPatterns("/shopadmin/getshoplist");
		permissionIR.excludePathPatterns("/shopadmin/getshopinitinfo");
		permissionIR.excludePathPatterns("/shopadmin/registershop");
		permissionIR.excludePathPatterns("/shopadmin/shopoperate");
		permissionIR.excludePathPatterns("/shopadmin/shopmanager");
		permissionIR.excludePathPatterns("/shopadmin/getshopmanagementinfo");
		// 扫码操作的路由
		permissionIR.excludePathPatterns("/shopadmin/addshopauthmap");
		permissionIR.excludePathPatterns("/shopadmin/adduserproductmap");
		permissionIR.excludePathPatterns("/shopadmin/exchangeaward");

	}

}
