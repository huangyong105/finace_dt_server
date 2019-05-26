package com.jit.wxs.security.authentication;

import com.jit.wxs.entity.SysRole;
import com.jit.wxs.entity.SysUser;
import com.jit.wxs.entity.SysUserRole;
import com.jit.wxs.service.SysRoleService;
import com.jit.wxs.service.SysUserRoleService;
import com.jit.wxs.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 默认 UserDetailService，通过用户名读取信息
 * @author jitwxs
 * @since 2019/1/8 23:34
 */
@Service
public class DefaultUserDetailsService implements UserDetailsService {
    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 从数据库中取出用户信息
        SysUser user = userService.getByName(username);

        // 判断用户是否存在
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        // 添加权限
        List<SysUserRole> userRoles = userRoleService.listByUserId(user.getId());
        if(CollectionUtils.isEmpty(userRoles)) {
            throw new UsernameNotFoundException("用户未分配角色");
        }
        for (SysUserRole userRole : userRoles) {
            SysRole role = roleService.getById(userRole.getRoleId());
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        Boolean accountNonExpired = true;

        /**
         * 用户的账户是否被锁定,被锁定的账户无法通过授权验证. true 账号未锁定
         */
         Boolean accountNonLocked = true;
         if (user.getState() == 1) {
             accountNonLocked =false ;
         }

        /**
         * 用户的凭据(pasword) 是否过期,过期的凭据不能通过验证. true 没有过期,false 已过期
         */
         Boolean credentialsNonExpired = false;


        // 返回UserDetails实现类
        User userDetails = new User(user.getName(),user.getPassword(), true,accountNonExpired,accountNonLocked,credentialsNonExpired, authorities);
        return userDetails;
    }
}
