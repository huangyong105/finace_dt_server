package com.roncoo.eshop.mapper;


import com.roncoo.eshop.model.ProjectManagementDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ProjectManagementMapper {

    @Select("select * from project_management where id = #{id,jdbcType=INTEGER} and is_online =1 and deleted =0")
    ProjectManagementDO selectByPrimaryKey(Integer id);

    @Select("select * from project_management where is_online=1 and deleted=0")
    List<ProjectManagementDO> selectAllPrimary();
}