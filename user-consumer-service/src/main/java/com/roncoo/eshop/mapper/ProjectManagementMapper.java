package com.roncoo.eshop.mapper;


import com.roncoo.eshop.model.ProjectManagementDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ProjectManagementMapper {

    @Select("select * from project_management where id = #{id} and is_online =1 and deleted =0")
    ProjectManagementDO selectByPrimaryKey(Long id);

    @Select("select * from project_management where is_online=1 and deleted=0 order by gmt_created desc")
    List<ProjectManagementDO> selectPrimaryList();

    @Insert("insert into project_management " +
            "(project_name,month_earnings,expected_risk_tolerance,min_margin,money_proportion,gmt_created,gmt_updated,is_online,deleted)" +
            " values (#{projectName},#{monthEarnings},#{expectedRiskTolerance},#{minMargin},#{moneyProportion},now(),now(),1,0)")
    void insertInvestment(ProjectManagementDO projectManagementDO);

    @Select("select * from project_management where deleted=0")
    List<ProjectManagementDO> selectAllPrimary();

    @Update("update project_management set is_online=#{isOnline} where id=#{id}")
    void updateline(ProjectManagementDO projectManagementDO);

    @Update("update project_management set " +
            "project_name=#{projectName}," +
            "month_earnings=#{monthEarnings}," +
            "expected_risk_tolerance=#{expectedRiskTolerance}," +
            "min_margin=#{minMargin}," +
            "money_proportion=#{moneyProportion}," +
            "gmt_created=now()," +
            "gmt_updated=now()," +
            "is_online=#{isOnline}")
    void updateProject(ProjectManagementDO projectManagementDO);
}