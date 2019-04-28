package com.roncoo.eshop.manager;

import cn.com.taiji.DTO.InvestmentDetailsDTO;
import com.roncoo.eshop.mapper.PayOrderMapper;
import com.roncoo.eshop.model.PayOrderDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayOrderManager {
    @Autowired
    PayOrderMapper payOrderMapper;
    public PayOrderDO savePayOrder(String orderCode, InvestmentDetailsDTO investmentDetailsDTO,Long userId){
        PayOrderDO payOrderDO = new PayOrderDO();
        payOrderDO.setOrderId(orderCode);
        payOrderDO.setUserId(userId);
        payOrderDO.setInputMargin(investmentDetailsDTO.getInputMargin());
        payOrderDO.setProjectName(investmentDetailsDTO.getProjectName());
        payOrderDO.setPayDescribe(investmentDetailsDTO.getProjectName());
        payOrderDO.setPayTitle("项目投资");
        payOrderDO.setPayState(0);
        payOrderMapper.insertPayOrder(payOrderDO);
        PayOrderDO payOrder = payOrderMapper.selectByOrderId(orderCode);
        return payOrder;
    }
}
