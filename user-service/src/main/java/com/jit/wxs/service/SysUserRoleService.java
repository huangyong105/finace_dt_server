package com.jit.wxs.service;

import com.jit.wxs.entity.SysUserRole;
import com.jit.wxs.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserRoleService {
    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Cacheable(value = "sysUserRoles",  key= "'sysUserRoles_'+#userId")
    public List<SysUserRole> listByUserId(Integer userId) {
        return userRoleMapper.listByUserId(userId);
    }
}
