package com.roncoo.eshop.mapper;



import com.roncoo.eshop.model.InvestmentDetailsDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InvestmentDetailsMapper {
    int deleteByPrimaryKey(Integer id);

    @Insert("insert into investment_details" +
            "(project_id,investmenter_id,project_name,month_earnings,expected_risk_tolerance,input_margin,money_proportion,input_margin_time,state,gmt_created,gmt_updated,deleted)" +
            "values (#{projectId},#{investmenterId},#{projectName},#{monthEarnings},#{expectedRiskTolerance},#{inputMargin},#{moneyProportion},#{inputMarginTime},1,now(),now(),0)")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn = "id")
    Long insert(InvestmentDetailsDO record);

    @Select("select * from investment_details where investmenter_id=#{userId}")
    List<InvestmentDetailsDO> selectByPrimaryKey(Long userId);


    int updateByPrimaryKeySelective(InvestmentDetailsDO record);

    int updateByPrimaryKey(InvestmentDetailsDO record);
}