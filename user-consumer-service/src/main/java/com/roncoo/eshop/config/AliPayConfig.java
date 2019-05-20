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
    @Value("${ali.privateKey}")
    private String privateKey;
    @Value("${ali.publicKey}")
    private String publicKey;
    @Value("${ali.serverUrl}")
    private String serverUrl;
    @Value("${ali.appid}")
    private String appid;
    @Bean
    public AlipayClient AlipayClientFactory(){
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl,appid,privateKey,"json","utf-8",publicKey,"RSA2");
        return alipayClient;
    }

    public static Logger getLOG() {
        return LOG;
    }

    public static void setLOG(Logger LOG) {
        AliPayConfig.LOG = LOG;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
