package com.jit.wxs.service;

import com.jit.wxs.entity.SysPermission;
import com.jit.wxs.mapper.SysPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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
}
