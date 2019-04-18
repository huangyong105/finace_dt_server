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

}
