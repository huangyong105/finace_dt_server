package com.roncoo.eshop.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliPayConfig {
    private static Logger LOG= LoggerFactory.getLogger(AliPayConfig.class);
    @Value("${privateKey}")
    private String privateKey;
    @Value("${publicKey}")
    private String publicKey;
    @Value("${serverUrl}")
    private String serverUrl;
    @Value("${appid}")
    private String appid;
    @Bean
    public AlipayClient AlipayClientFactory(){
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl,appid,privateKey,"json","utf-8",publicKey,"RSA2");
        return alipayClient;
    }
}
