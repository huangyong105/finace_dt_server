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

    @Select("select id from investment_details where UNIX_TIMESTAMP(input_margin_time)=UNIX_TIMESTAMP(#{inputMarginTime}) and investmenter_id = #{investmenterId}")
    Long selectId(@Param("inputMarginTime")String inputMarginTime,@Param("investmenterId")Long investmenterId);

    @Select("select * from investment_details where investmenter_id=#{userId} order by gmt_created desc")
    List<InvestmentDetailsDO> selectByPrimaryKey(Long userId);

    @Select("select * from investment_details where state = 3 and gmt_created < #{endTime} and gmt_created > #{beginTime}")
    List<InvestmentDetailsDO> selectByRefund(@Param("endTime") String endTime, @Param("beginTime") String beginTime);

    @Select("select * from investment_details where state = 3 and gmt_created < #{endTime} and gmt_created > #{beginTime} and project_id = #{productId}")
    List<InvestmentDetailsDO> selectByRefundAndId(@Param("endTime") String endTime, @Param("beginTime") String beginTime,@Param("productId")Long productId);

    @Select("select * from investment_details where id=#{id}")
    InvestmentDetailsDO selectById(Long id);

    @Update("update investment_details set state = #{state} where id = #{id}")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn = "id")
    Long updateState(@Param("id") Long id,@Param("state") Integer state);


    int updateByPrimaryKeySelective(InvestmentDetailsDO record);

    int updateByPrimaryKey(InvestmentDetailsDO record);
}