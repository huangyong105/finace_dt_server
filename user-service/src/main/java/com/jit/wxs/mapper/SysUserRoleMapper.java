package com.jit.wxs.mapper;

import com.jit.wxs.entity.SysRole;
import com.jit.wxs.entity.SysUserRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {
    @Select("SELECT * FROM sys_user_role WHERE user_id = #{userId}")
    public List<SysUserRole> listByUserId(Integer userId);

    @Select("SELECT * FROM sys_user_role WHERE role_id = #{roleId}")
    public List<SysUserRole> listByRoleId(Integer roleId);

    @Select("DELETE FROM sys_user_role WHERE user_id = #{userId}")
    public Long delete(Integer userId);
    /**
     * 创建系统角色
     * @param sysUserRoles
     * @return
     */
    @Insert({ "<script>" +
            "insert into sys_user_role(user_id ,role_id) values"
            +" <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">"
            +"(#{item.userId,jdbcType=INTEGER}, #{item.roleId,jdbcType=INTEGER})"
            +"</foreach>"
            +"</script>" })
    Integer  createSysUserRole (List<SysUserRole> sysUserRoles) ;
}
