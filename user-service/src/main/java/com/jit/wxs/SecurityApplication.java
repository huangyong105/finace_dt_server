package com.jit.wxs;

import com.jit.wxs.security.SecurityConstants;
import com.jit.wxs.security.validate.code.VerifyServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient
@RefreshScope
@EnableTransactionManagement
@ServletComponentScan
@EnableScheduling
@EnableAsync
@EnableCaching
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);

    }

    /**
     * 注入验证码servlet
     */
    @Bean
    public ServletRegistrationBean indexServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new VerifyServlet());
        registration.addUrlMappings(SecurityConstants.VALIDATE_CODE_PIC_URL);
        return registration;
    }




}
