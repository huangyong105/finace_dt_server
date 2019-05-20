package com.roncoo.eshop.mapper;

import com.roncoo.eshop.model.PayOrderDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

@Mapper
public interface PayOrderMapper {
    @Insert("insert into pay_order (pay_order_id,userId,input_margin,project_name,pay_describe,pay_title,gmt_created,gmt_updated,pay_state,project_id,pay_way)" +
            "values (#{payOrderId},#{userId},#{inputMargin},#{projectName},#{payDescribe},#{payTitle},now(),now(),#{payState},#{projectId},#{payWay})")
    void insertPayOrder(PayOrderDO payOrderDO);

    @Select("select * from pay_order where pay_order_id = #{payOrderId}")
    PayOrderDO selectByOrderId(String payOrderId);

    @Update("update pay_order set order_id = #{orderId} and pay_state = #{payState} where pay_order_id = #{payOrderId}")
    void updateOrderIdAndState(PayOrderDO payOrderDO);

    @Update("update pay_order set pay_state = #{payState} where pay_order_id = #{payOrderId}")
    void updateState(PayOrderDO payOrderDO);

}
