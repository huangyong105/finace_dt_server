package com.roncoo.eshop.client;

import com.roncoo.eshop.model.User;
import com.roncoo.eshop.result.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:yong.huang
 * @Date: 2019/4/10 22:04
 */
@FeignClient(value = "user-account-service",url="http://47.101.147.30:8770/")
public interface UserClient {
    @PostMapping("/getUserInfo")
    public Result<User> getUserInfo(@RequestHeader("token") String token) throws Exception ;
}