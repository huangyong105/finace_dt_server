package com.jit.wxs.mapper;

import com.jit.wxs.entity.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysUserMapper {
    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    SysUser selectById(Integer id);

    @Select("SELECT * FROM sys_user WHERE `delete` = #{delete}")
    List<SysUser> loadUsers (int delete) ;

    @Select("SELECT * FROM sys_user WHERE name = #{name}")
    SysUser selectByName(String name);

    @Select("SELECT * FROM sys_user WHERE mobile = #{mobile}")
    SysUser selectByMobile(String mobile);

    @Insert({ "insert into sys_user(name, mobile, password,email,state, create_time,update_time) values(#{name}, #{mobile}, #{password}, #{email},#{state}, #{createTime, jdbcType=TIMESTAMP}, #{updateTime, jdbcType=TIMESTAMP})" })
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn = "id")
    Integer  createSysUser (SysUser sysUser) ;

    @Update({ "<script>" +
            "update sys_user "
            +"set "
            + "<if test='name!=null'>"
            + "name = #{name},"
            + "</if>"
            + "<if test='mobile!=null'>"
            +"mobile = #{mobile},"
            + "</if>"
            + "<if test='password!=null'>"
            +"password = #{password},"
            + "</if>"
            + "<if test='delete!=null'>"
            +"`delete` = #{delete} ,"
            + "</if>"
            + "<if test='state!=null'>"
            +"state = #{state},"
            + "</if>"
            + "<if test='updateTime!=null'>"
            + "update_time = #{updateTime, jdbcType=TIMESTAMP} "
            + "</if>"
            +"where id = #{id}"
            +"</script>" })
    Integer  update (SysUser sysUser) ;
}
