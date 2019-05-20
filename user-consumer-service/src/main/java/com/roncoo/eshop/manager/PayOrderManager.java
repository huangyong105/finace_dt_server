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
    public PayOrderDO saveAliPayOrder(String orderCode, InvestmentDetailsDTO investmentDetailsDTO,Long userId,Integer payWay,String tradeNo){
        PayOrderDO payOrderDO = new PayOrderDO();
        payOrderDO.setPayOrderId(orderCode);
        payOrderDO.setUserId(userId);
        payOrderDO.setInputMargin(investmentDetailsDTO.getInputMargin());
        payOrderDO.setProjectName(investmentDetailsDTO.getProjectName());
        payOrderDO.setPayDescribe(investmentDetailsDTO.getProjectName());
        payOrderDO.setPayTitle("项目投资");
        payOrderDO.setPayState(0);
        payOrderDO.setProjectId(investmentDetailsDTO.getProjectId());
        payOrderDO.setPayWay(payWay);
        payOrderDO.setTradeNo(tradeNo);
        payOrderMapper.insertPayOrder(payOrderDO);
        PayOrderDO payOrder = payOrderMapper.selectByOrderId(orderCode);
        return payOrder;
    }

    public PayOrderDO saveWxPayOrder(String orderCode, InvestmentDetailsDTO investmentDetailsDTO,Long userId,Integer payWay){
        PayOrderDO payOrderDO = new PayOrderDO();
        payOrderDO.setPayOrderId(orderCode);
        payOrderDO.setUserId(userId);
        payOrderDO.setInputMargin(investmentDetailsDTO.getInputMargin());
        payOrderDO.setProjectName(investmentDetailsDTO.getProjectName());
        payOrderDO.setPayDescribe(investmentDetailsDTO.getProjectName());
        payOrderDO.setPayTitle("项目投资");
        payOrderDO.setPayState(0);
        payOrderDO.setProjectId(investmentDetailsDTO.getProjectId());
        payOrderDO.setPayWay(payWay);
        payOrderMapper.insertPayOrder(payOrderDO);
        PayOrderDO payOrder = payOrderMapper.selectByOrderId(orderCode);
        return payOrder;
    }
}
