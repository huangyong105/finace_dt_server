package com.jit.wxs.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:yong.huang
 * @Date: 2019/4/18 20:45
 */
@Data
public class SysUserReq implements Serializable {

    private Integer id;

    private String name;

    private String mobile;

    private String password;

    private String email ;

    private Integer state ;


    private String [] perms ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String[] getPerms() {
        return perms;
    }

    public void setPerms(String[] perms) {
        this.perms = perms;
    }
}
