package com.jit.wxs.service;

import com.jit.wxs.entity.SysRole;
import com.jit.wxs.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SysRoleService {
    @Autowired
    private SysRoleMapper roleMapper;
  //  @Cacheable(value={"roles"}, key= "#root.methodName+'['+#id+']'")
    public SysRole getById(Integer id) {
        return roleMapper.selectById(id);
    }
    //@Cacheable(value={"roles"}, key= "#root.methodName+'['+#p0+']'")
    public SysRole getByName(String roleName) {
        return roleMapper.selectByName(roleName);
    }
}
