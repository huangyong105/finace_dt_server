package com.roncoo.eshop.mapper;

import com.roncoo.eshop.model.PayOrderDO;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface PayOrderMapper {
    @Insert("insert into pay_order (pay_order_id,userId,userName,input_margin,project_name,pay_describe,pay_title,gmt_created,gmt_updated,pay_state,project_id,pay_way)" +
            "values (#{payOrderId},#{userId},#{userName},#{inputMargin},#{projectName},#{payDescribe},#{payTitle},now(),now(),#{payState},#{projectId},#{payWay})")
    void insertPayOrder(PayOrderDO payOrderDO);

    @Select("select * from pay_order where pay_order_id = #{payOrderId}")
    PayOrderDO selectByOrderId(String payOrderId);

    @Update("update pay_order set order_id = #{orderId},pay_state = #{payState} where pay_order_id = #{payOrderId}")
    void updateOrderIdAndState(PayOrderDO payOrderDO);

    @Update("update pay_order set pay_state = #{payState} where pay_order_id = #{payOrderId}")
    void updateState(PayOrderDO payOrderDO);

    @Select("select * from pay_order where order_id = #{id} and pay_state = 1")
    PayOrderDO selectById(Long id);

    @Select("select * from pay_order where pay_state = 1 and gmt_created < #{endTime} and gmt_created > #{beginTime}")
    List<PayOrderDO> selectBySearchSuccess(@Param("endTime") String endTime, @Param("beginTime") String beginTime);

    @Select("select * from pay_order where pay_state = 1 and gmt_created < #{endTime} and gmt_created > #{beginTime} and project_id = #{productId}")
    List<PayOrderDO> selectBySearchSuccessAndId(@Param("endTime") String endTime, @Param("beginTime") String beginTime,@Param("productId")Long productId);


}
