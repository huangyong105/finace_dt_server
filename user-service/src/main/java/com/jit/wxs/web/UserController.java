package com.jit.wxs.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.jit.wxs.entity.Result;
import com.jit.wxs.entity.SysPermission;
import com.jit.wxs.entity.SysUser;
import com.jit.wxs.entity.SysUserRole;
import com.jit.wxs.service.SysPermissionService;
import com.jit.wxs.service.SysUserRoleService;
import com.jit.wxs.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/uc")
public class UserController {

	@Autowired
	private SysUserService userService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysPermissionService permissionService;
	
	@RequestMapping("/findAllUsers")
	public Result findAllUsers(Authentication authentication) {
		List<SysUser> sysUsers = userService.loadUsers();
		if (Objects.isNull(sysUsers)) {
			return Result.ofError(-1,"未创建用户");
		}
		sysUsers=sysUsers.stream().map(sysUser -> {
			List<String> permissions = new ArrayList<>();
			List<SysUserRole> sysUserRoles = sysUserRoleService.listByUserId(sysUser.getId());
			sysUserRoles.stream().forEach(sysUserRole -> {
				List<SysPermission> permissionList = permissionService.listByRoleId(sysUserRole.getRoleId());
				List<String> permissionLists =permissionList.stream().map(sysPermission -> {
					return sysPermission.getName();
				}).collect(Collectors.toList());
				permissions.addAll(permissionLists);
			});
			StringBuffer sb = new StringBuffer();
			int index = 0 ;
			for (String perm : permissions) {
				if (index>0) {
					sb.append(",");
				}
				sb.append(perm);
				index++;
			}
			sysUser.setPerms(sb.toString());
			return sysUser;
		}).collect(Collectors.toList());
		return Result.ofSuccess(sysUsers);
	}


}
