package com.jit.wxs.service;

import com.jit.wxs.entity.SysUser;
import com.jit.wxs.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SysUserService {
    @Autowired
    private SysUserMapper userMapper;
   // @Cacheable(value={"users"}, key= "#root.methodName+'['+#id+']'")
    public SysUser getById(Integer id) {
        return userMapper.selectById(id);
    }
    //@Cacheable(value={"users"}, key= "#root.methodName+'['+#name+']'")
    public SysUser getByName(String name) {
        return userMapper.selectByName(name);
    }

    @Cacheable(value={"users"}, key= "#root.methodName+'['+#mobile+']'")
    public SysUser getByMobile(String mobile) {
        return userMapper.selectByMobile(mobile);
    }
}
