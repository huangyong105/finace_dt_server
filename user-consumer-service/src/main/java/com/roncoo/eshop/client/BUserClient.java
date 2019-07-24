package com.roncoo.eshop.client;



import cn.com.taiji.DTO.SysUser;
import cn.com.taiji.data.Token;
import cn.com.taiji.data.User;
import cn.com.taiji.data.UserEntity;
import cn.com.taiji.result.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:yong.huang
 * @Date: 2019/4/10 22:04
 */

@FeignClient(value = "user-service",url="http://47.112.123.113:8770/")
public interface BUserClient {
    @PostMapping("/uc/findAllUsersByPerm")
    public Result<List<SysUser>> findAllUsersByPerm();

}

