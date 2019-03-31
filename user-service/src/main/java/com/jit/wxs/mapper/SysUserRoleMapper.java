package com.jit.wxs.mapper;

import com.jit.wxs.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {
    @Select("SELECT * FROM sys_user_role WHERE user_id = #{userId}")
    public List<SysUserRole> listByUserId(Integer userId);
}
