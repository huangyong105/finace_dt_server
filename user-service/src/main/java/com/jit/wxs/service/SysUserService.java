package com.jit.wxs.service;

import com.jit.wxs.entity.SysUser;
import com.jit.wxs.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class SysUserService {
    @Autowired
    private SysUserMapper userMapper;
/*    @Cacheable(value="sys_user", key= "#id")*/
    public SysUser getById(Integer id) {
        return userMapper.selectById(id);
    }

    public List<SysUser> loadUsers() {
        return userMapper.loadUsers(0);
    }

/*    @Cacheable(value="sys_user", key= "#name")*/
    public SysUser getByName(String name) {
        return userMapper.selectByName(name);
    }

/*    @Cacheable(value="sys_user", key= "#mobile")*/
    public SysUser getByMobile(String mobile) {
        return userMapper.selectByMobile(mobile);
    }

/*    @CacheEvict(value="sys_user", allEntries=true)*/
    public SysUser createUser (SysUser sysUser) {
       userMapper.createSysUser(sysUser);

        if (Objects.nonNull(sysUser.getId())) {
            sysUser =this.getById(sysUser.getId());
        }
        return sysUser;
    }

/*    @CacheEvict(value="sys_user", allEntries=true)*/
    public SysUser updateUser (SysUser sysUser) {
        sysUser.setUpdateTime(new Date());
         userMapper.update(sysUser);
        if (Objects.nonNull(sysUser.getId())) {
            sysUser =this.getById(sysUser.getId());
        }
        return sysUser;
    }


}
