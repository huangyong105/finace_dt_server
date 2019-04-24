package com.roncoo.eshop.client;


import cn.com.taiji.data.Result;
import cn.com.taiji.data.Token;
import cn.com.taiji.data.User;
import cn.com.taiji.data.UserEntity;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:yong.huang
 * @Date: 2019/4/10 22:04
 */

@FeignClient(value = "service-api",url="http://47.101.147.30:8181/")
public interface UserClient {
    @PostMapping("/auth/getUserInfo")
    public Result<User> getUserInfo(@RequestHeader("token") String token) throws Exception ;

    @PostMapping("/auth/realNameCertification")
    public Result realNameCertification(@RequestBody User user);

    @PostMapping("/auth/bindCard")
    public Result bindCard(@RequestBody User user);

    @PostMapping("/auth/login")
    public Result<Token> login(@RequestParam(value = "account")String account,@RequestParam(value = "password") String password);

    @RequestMapping(path = "/auth/users", method = RequestMethod.POST, name = "createUser")
    public Result<UserEntity> createUser(@RequestBody UserEntity userEntity);

    @PostMapping("/auth/sendRegisterSmsCode")
    public Result sendRegisterSmsCode (@RequestParam(value ="mobile") String mobile);

    @PostMapping("/auth/sendChangePasswordSmsCode")
    public Result sendChangePasswordSmsCode(@RequestParam(value ="mobile") String mobile);

    @PostMapping("/auth/sendFindPasswordSmsCode")
    public Result sendFindPasswordSmsCode (@RequestParam(value ="mobile") String mobile);

    @PostMapping("/auth/changePassword")
    public Result changePassword(@RequestBody UserEntity userEntity);

    @RequestMapping(path = "/auth/findPassword", name = "findPassword")
    public Result  findPassword (@RequestBody UserEntity userEntity);

}

