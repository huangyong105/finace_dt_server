package com.roncoo.eshop.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:yong.huang
 * @Date: 2019/3/28 20:13
 */
@FeignClient(value = "user-service",url="http://47.101.147.30:8770/")
public interface HelloServcie {
    @RequestMapping(value = "/uc/helloWorld")
    @ResponseBody
    public String helloWorld();
}
