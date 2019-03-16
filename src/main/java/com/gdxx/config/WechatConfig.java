package com.gdxx.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;

@Configuration
public class WechatConfig {
 
    @Value("${wechat.appid}")
    public String appid;
 
    @Value("${wechat.appsecret}")
    public String appsecret;
 
    @Value("${wechat.token}")
    public String token;
 
    public static String  prefix_url;
    /**
     * 如果出现 org.springframework.beans.BeanInstantiationException
     * https://github.com/Wechat-Group/weixin-java-tools-springmvc/issues/7
     * 请添加以下默认无参构造函数
     */
     protected WechatConfig(){}
    
    /**
     * 为了生成自定义菜单使用的构造函数，其他情况Spring框架可以直接注入
     *
     * @param appid
     * @param appsecret
     * @param token
     * @param aesKey
     */
    protected WechatConfig(String appid, String appsecret, String token) {
        this.appid = appid;
        this.appsecret = appsecret;
        this.token = token;
    }
 
    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(this.appid);
        configStorage.setSecret(this.appsecret);
        configStorage.setToken(this.token);
        return configStorage;
    }
 
    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }
 
}
