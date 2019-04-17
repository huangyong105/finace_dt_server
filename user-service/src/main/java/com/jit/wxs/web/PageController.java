package com.jit.wxs.web;

import com.jit.wxs.entity.Result;
import com.jit.wxs.entity.SysPermission;
import com.jit.wxs.security.SecurityConstants;
import com.jit.wxs.service.SysPermissionService;
import com.jit.wxs.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
public class PageController {
    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private SysRoleService roleService;
    /**
     * 跳转到登陆成功页
     */
    @RequestMapping(SecurityConstants.LOGIN_SUCCESS_URL)
    public String showSuccessPage() {
        return "pages/application-list.html";
    }

    @RequestMapping("/loadUrls")
    @ResponseBody
    public Result loadUrls (Authentication authentication) {
        // 获得loadUserByUsername()方法的结果
        User user = (User)authentication.getPrincipal();
        // 获得loadUserByUsername()中注入的角色
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        List<SysPermission> permissionsList = new ArrayList<>();
        // 遍历用户所有角色
        for(GrantedAuthority authority : authorities) {
            String roleName = authority.getAuthority();
            Integer roleId = roleService.getByName(roleName).getId();
            // 得到角色所有的权限
            List<SysPermission> permissionList = permissionService.listByRoleId(roleId);
            permissionsList.addAll(permissionList);

        }
        return Result.ofSuccess(permissionsList);
    }

    /**
     * 跳转到登录页
     */
    @RequestMapping(SecurityConstants.UN_AUTHENTICATION_URL)
    public String showAuthenticationPage() {
        return "pages/page-login.html";

    }




}
