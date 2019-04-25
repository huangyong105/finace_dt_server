package com.jit.wxs.mapper;


import com.jit.wxs.entity.CUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CUserMapper {
    @Select("<script>select id,account,email,state from user where <if test='account!=null'>account = #{account}</if></script>")
    List<CUser> getUserList(CUser user);

    @Select("select * from user where id = #{id}")
    CUser getUserInfo(CUser user);
}
