package com.jit.wxs.web;

import java.util.*;
import java.util.stream.Collectors;


import com.jit.wxs.entity.*;
import com.jit.wxs.service.SysPermissionService;
import com.jit.wxs.service.SysUserRoleService;
import com.jit.wxs.service.SysUserService;
import com.jit.wxs.util.BeanConverter;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
            return Result.ofError(-1, "未创建用户");
        }
        sysUsers = sysUsers.stream().map(sysUser -> {
            List<String> permissions = new ArrayList<>();
            List<Integer> idList = new ArrayList<Integer>();
            List<SysUserRole> sysUserRoles = sysUserRoleService.listByUserId(sysUser.getId());
            sysUserRoles.stream().forEach(sysUserRole -> {
                List<SysPermission> permissionList = permissionService.listByRoleId(sysUserRole.getRoleId());
                List<String> permissionLists = permissionList.stream().map(sysPermission -> {
                    idList.add(sysPermission.getId());
                    return sysPermission.getName();
                }).collect(Collectors.toList());
                permissions.addAll(permissionLists);
            });
            StringBuffer sb = new StringBuffer();
            int index = 0;
            for (String perm : permissions) {
                if (index > 0) {
                    sb.append(",");
                }
                sb.append(perm);
                index++;
            }
            //设置idList
            sysUser.setIdList(idList);
            sysUser.setPerms(sb.toString());
            return sysUser;
        }).collect(Collectors.toList());
        return Result.ofSuccess(sysUsers);
    }


    @RequestMapping("/findAllUsersByPerm")
    public Result findAllUsersByPerm() {
        List<SysUserRole> sysUserRoles = sysUserRoleService.listByRoleId(3);
        List<SysUser> sysUserList = new ArrayList<>();
        if (Objects.isNull(sysUserRoles)) {
            return Result.ofError(-1, "未创建用户");
        }
        sysUserList = sysUserRoles.stream().map(sysUserRole -> {
            List<String> permissions = new ArrayList<>();
            List<Integer> idList = new ArrayList<Integer>();
            SysUser sysUser = userService.getById(sysUserRole.getUserId());
            List<SysPermission> permissionList = permissionService.listByRoleId(sysUserRole.getRoleId());
            List<String> permissionLists = permissionList.stream().map(sysPermission -> {
                idList.add(sysPermission.getId());
                return sysPermission.getName();
            }).collect(Collectors.toList());
            permissions.addAll(permissionLists);
            StringBuffer sb = new StringBuffer();
            int index = 0;
            for (String perm : permissions) {
                if (index > 0) {
                    sb.append(",");
                }
                sb.append(perm);
                index++;
            }
            //设置idList
            sysUser.setIdList(idList);
            sysUser.setPerms(sb.toString());
            return sysUser;
        }).collect(Collectors.toList());
        return Result.ofSuccess(sysUserList);
    }

    @RequestMapping("/createUser")
    public Result createUser(SysUserReq sysUserReq) {
        SysUser sysUser = BeanConverter.convert(sysUserReq, SysUser.class);
        if (Objects.nonNull(sysUser.getId())) {
            sysUser.setUpdateTime(new Date());
            sysUser = userService.updateUser(sysUser);
            bindUserRole(sysUserReq, sysUser);
            return Result.ofSuccess(sysUser);
        }
        sysUser.setCreateTime(new Date());
        sysUser = userService.createUser(sysUser);
        bindUserRole(sysUserReq, sysUser);
        return Result.ofSuccess(sysUser);
    }

    private void bindUserRole(SysUserReq sysUserReq, SysUser sysUser) {
        sysUserRoleService.delete(sysUser.getId());
        if (Objects.nonNull(sysUserReq.getPerms())) {
            List<SysUserRole> sysUserRoles = new ArrayList<>();
            for (String perm : sysUserReq.getPerms()) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(NumberUtils.toInt(perm));
                sysUserRole.setUserId(sysUser.getId());
                sysUserRoles.add(sysUserRole);
            }
            if (!CollectionUtils.isEmpty(sysUserRoles)) {
                sysUserRoleService.createSysUserRole(sysUserRoles);
            }
        }
    }

    @RequestMapping("/deleteSysUser")
    @ResponseBody
    public Result deleteSysUser(@RequestBody SysUserReq sysUserReq) {
        SysUser sysUser = BeanConverter.convert(sysUserReq, SysUser.class);
        sysUser.setUpdateTime(new Date());
        sysUser.setDelete(1);
        sysUser = userService.updateUser(sysUser);
        if (Objects.nonNull(sysUser)) {
            return Result.ofSuccess(sysUser);
        }
        return Result.ofError(1, "删除失败");
    }


/*    public void exportExcel(List<SmsReportErrorInfoDTO> list, String sysCode){
        InputStream is = null;
        String[] headers_book = { "任务ID", "短信平台", "业务系统", "手机号", "错误码", "错误信息", "备注", "统计时间", "短信内容"};
        is = exportExcelService.exportExcel("短信发送失败记录",headers_book, list);
        String text = "各位好："+DateUtils.getReqDate(new Date())+"短信发送失败记录统计信息";
        String subject = "短信发送失败记录";
        String[] to = getEmailTo(sysCode);
//		String[] cc = getEmailCc(sysCode);
        EmailModelDTO email = new EmailModelDTO(from, to, null, subject, text, is);
        logger.info("短信发送状态邮件信息 {}", JSONArray.toJSON(email));
        mailService.sendAttachmentsMail(email);
    }*/

  /* public void sendMail () {
       // 发送邮件
       String[] to = null;
       String subject = "短信发送失败通知";
      // logger.info("短信平台通知邮件信息（邮件）：{}", content);
     //  to = str.toArray(new String[str.size()]);
       EmailModelDTO email = new EmailModelDTO(from, to, null, subject, content, null);
       mailService.sendHtmlMail(email);

   }*/

}
