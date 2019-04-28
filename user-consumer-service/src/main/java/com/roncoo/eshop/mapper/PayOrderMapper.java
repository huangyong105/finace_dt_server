package com.roncoo.eshop.mapper;

import com.roncoo.eshop.model.PayOrderDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PayOrderMapper {
    @Insert("insert into pay_order (orderId,userId,input_margin,project_name,pay_describe,pay_title,gmt_created,gmt_updated,pay_state)" +
            "values (#{orderId},#{userId},#{inputMargin},#{projectName},#{payDescribe},#{payTitle},now(),now()   ,#{payState})")
    void insertPayOrder(PayOrderDO payOrderDO);

    @Select("select * from pay_order where OrderId = #{orderId}")
    PayOrderDO selectByOrderId(String orderId);
}
