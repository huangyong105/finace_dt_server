package com.roncoo.eshop.mapper;



import com.roncoo.eshop.model.InvestmentDetailsDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InvestmentDetailsMapper {
    int deleteByPrimaryKey(Integer id);

    @Insert("insert into investment_details" +
            "(project_id,investmenter_id,project_name,month_earnings,expected_risk_tolerance,input_margin,money_proportion,input_margin_time,state,gmt_created,gmt_updated,deleted)" +
            "values (#{projectId},#{investmenterId},#{projectName},#{monthEarnings},#{expectedRiskTolerance},#{inputMargin},#{moneyProportion},#{inputMarginTime},1,now(),now(),0)")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn = "id")
    Long insert(InvestmentDetailsDO record);

    @Select("select * from investment_details where investmenter_id=#{userId} order by gmt_created desc")
    List<InvestmentDetailsDO> selectByPrimaryKey(Long userId);

    @Select("select * from investment_details where id=#{id}")
    InvestmentDetailsDO selectById(Long id);

    @Update("update investment_details set state = #{state} where id = #{id}")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn = "id")
    Long updateState(Long id,Integer state);


    int updateByPrimaryKeySelective(InvestmentDetailsDO record);

    int updateByPrimaryKey(InvestmentDetailsDO record);
}