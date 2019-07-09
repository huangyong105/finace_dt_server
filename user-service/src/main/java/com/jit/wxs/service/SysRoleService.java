package com.jit.wxs.service;

import com.jit.wxs.entity.SysRole;
import com.jit.wxs.entity.SysUser;
import com.jit.wxs.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SysRoleService {
    @Autowired
    private SysRoleMapper roleMapper;
/*    @Cacheable(value="roles", key= "'roles_'+#id")*/
    public SysRole getById(Integer id) {
        return roleMapper.selectById(id);
    }
/*    @Cacheable(value="roles", key= "'roles_'+#p0")*/
    public SysRole getByName(String roleName) {
        return roleMapper.selectByName(roleName);
    }

/*    @CacheEvict(value="roles", allEntries=true)*/
    public SysRole createUser (SysRole sysRole) {
        Integer id = roleMapper.createSysRole(sysRole);
        if (Objects.nonNull(id)) {
            sysRole =this.getById(id);
        }
        return sysRole;
    }
}
