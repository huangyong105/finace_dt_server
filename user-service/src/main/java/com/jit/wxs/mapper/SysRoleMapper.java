package com.jit.wxs.mapper;

import com.jit.wxs.entity.SysRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysRoleMapper {
    @Select("SELECT * FROM sys_role WHERE id = #{id}")
    SysRole selectById(Integer id);

    @Select("SELECT * FROM sys_role WHERE name = #{roleName}")
    SysRole selectByName(String roleName);

    /**
     * 创建系统角色
     * @param sysRole
     * @return
     */
    @Insert({ "insert into sys_user(name,create_time,update_time) values(#{name}, #{createTime, jdbcType=TIMESTAMP}, #{updateTime, jdbcType=TIMESTAMP})" })
    Integer  createSysRole (SysRole sysRole) ;

}
