package com.jit.wxs.service;

import com.jit.wxs.entity.Result;
import com.jit.wxs.entity.SysPermission;
import com.jit.wxs.mapper.SysPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class SysPermissionService {
    @Autowired
    private SysPermissionMapper permissionMapper;

    /**
     * 获取指定角色所有权限
     */
    //@Cacheable(value="permissions", key= "'permissions_'+#p0")
    public List<SysPermission> listByRoleId( Integer roleId) {
        return permissionMapper.listByRoleId(roleId);
    }

    public Result telNumberJudge(String telNum){
         String REGEX_MOBILE = "^[1][3,4,5,8][0-9]{9}$";
         if(Pattern.matches(REGEX_MOBILE, telNum)){
             return Result.ofSuccess();
         }else{
             return Result.ofError(500,"手机号格式不正确，请重新输入!");
         }
    }

    public Result emailJudge(String email){
        String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (Pattern.matches(REGEX_EMAIL, email)){
            return Result.ofSuccess();
        }else{
            return Result.ofError(500,"邮箱格式不正确,请重新输入!");
        }
    }

    public Result percentJudge(String percent){
        String REGEX_PERCENT = "^(\\d|[1-9]\\d|100)$";
        if (Pattern.matches(REGEX_PERCENT,percent)){
            if (Integer.parseInt(percent)>=0 && Integer.parseInt(percent)<=100){
                return Result.ofSuccess();
            }
        }
        return Result.ofError(500,"百分数必须为整数及合法格式，请重新输入!");
    }
}
