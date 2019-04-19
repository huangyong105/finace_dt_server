package com.jit.wxs.entity;

import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUser implements Serializable {
    static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String mobile;

    private String password;

    private String email ;

    private Integer state ;

    private Integer delete ;

    private Date createTime ;

    private Date  updateTime ;

    @Transient
    private String perms;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }


}
