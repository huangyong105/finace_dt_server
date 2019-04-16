package com.roncoo.eshop.client;







import cn.com.taiji.user.User;
import com.roncoo.eshop.result.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:yong.huang
 * @Date: 2019/4/10 22:04
 */

@FeignClient(value = "user-account-service",url="http://47.101.147.30:8181/")
public interface UserClient {
    @PostMapping("/auth/getUserInfo")
    public Result<User> getUserInfo(@RequestHeader("token") String token) throws Exception ;

    @PostMapping("/auth/realNameCertification")
    public Result realNameCertification(@RequestBody User user);

    @PostMapping("/auth/bindCard")
    public Result bindCard(@RequestBody User user);
}

