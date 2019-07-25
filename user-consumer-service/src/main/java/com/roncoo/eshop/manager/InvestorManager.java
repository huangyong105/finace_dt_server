package com.roncoo.eshop.manager;


import cn.com.taiji.DTO.InvestmentDetailsDTO;
import cn.com.taiji.data.Result;
import cn.com.taiji.data.UserEntity;
import cn.com.taiji.page.PageInfo;
import cn.com.taiji.page.PageResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.github.pagehelper.PageHelper;
import com.roncoo.eshop.client.UserClient;
import com.roncoo.eshop.config.AliPayConfig;
import com.roncoo.eshop.converter.BeanConverter;
import com.roncoo.eshop.mapper.InvestmentDetailsMapper;
import com.roncoo.eshop.mapper.PayOrderMapper;
import com.roncoo.eshop.model.AlipayNotifyParam;
import com.roncoo.eshop.model.AlipayRefund;
import com.roncoo.eshop.model.InvestmentDetailsDO;
import com.roncoo.eshop.model.PayOrderDO;
import com.roncoo.eshop.util.FtpUtil;
import com.roncoo.eshop.web.controller.InvestorManagementController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author dongyuan
 * @date 2019-03-24 22:45
 **/
@Service
public class InvestorManager {
    private static Logger LOG= LoggerFactory.getLogger(InvestorManager.class);
    @Autowired
    FtpUtil ftpUtil;
    @Autowired
    InvestmentDetailsMapper investmentDetailsMapper;
    @Autowired
    PayOrderMapper payOrderMapper;
    @Autowired
    AliPayConfig aliPayConfig;
    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private UserClient userClient;

    public String uploadIdPhoto(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (file.isEmpty()){
            return null;
        }
        String newName = ftpUtil.getFileSuffName(fileName);
        boolean upload = ftpUtil.uploadFile(file.getInputStream(), newName);
        if (upload){
            return newName;
        }
        return null;
    }

    public void downloadIdPhoto(String fileName,OutputStream ops){
        ftpUtil.downloadFile(fileName,ops);
    }

    public PageResult<InvestmentDetailsDTO> getInvestmentDetailsDOSByuserId(Long userID,Integer currentPage,Integer pageSize){
        PageHelper.startPage(currentPage, pageSize);
        List<InvestmentDetailsDO> investmentDetailsDOS = investmentDetailsMapper.selectByPrimaryKey(userID);
        com.github.pagehelper.PageInfo pageInfo = new com.github.pagehelper.PageInfo(investmentDetailsDOS);
        List<InvestmentDetailsDTO> dtos = BeanConverter.batchConvert(investmentDetailsDOS, InvestmentDetailsDTO.class);
        PageInfo pageInfo1 = new PageInfo();
        pageInfo1.setCurrentPage(currentPage);
        pageInfo1.setPageSize(pageSize);
        pageInfo1.setTotalCount(pageInfo.getTotal());
        pageInfo1.setTotalPage(pageInfo.getPages());
        PageResult<InvestmentDetailsDTO> pageResult = new PageResult<>(dtos, pageInfo1);
        return pageResult;
    }

    public List<InvestmentDetailsDTO> getInvestmentDetailsDTOByPaySaccess(InvestmentDetailsDTO investmentDetailsDTO){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置格式
        String timeEnd=format.format(investmentDetailsDTO.getEndTime());
        String timeBegin=format.format(investmentDetailsDTO.getBeginTime());
        List<PayOrderDO> payOrderDOS = null;
        if(investmentDetailsDTO.getProjectId()==null) {
            payOrderDOS = payOrderMapper.selectBySearchSuccess(timeEnd, timeBegin);
        }else{
            payOrderDOS = payOrderMapper.selectBySearchSuccessAndId(timeEnd, timeBegin, investmentDetailsDTO.getProjectId());
        }
        List<InvestmentDetailsDTO> investmentDetailsDTOS = new ArrayList<>();
        for(PayOrderDO payOrderDO:payOrderDOS){
            InvestmentDetailsDTO investmentDetails = new InvestmentDetailsDTO();
            investmentDetails.setProjectName(payOrderDO.getProjectName());
            investmentDetails.setProjectId(payOrderDO.getProjectId());
            investmentDetails.setPayOrderId(payOrderDO.getPayOrderId());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            investmentDetails.setPayTime(simpleDateFormat.format(payOrderDO.getGmtCreated()));
            Result<UserEntity> result = userClient.getUserById(payOrderDO.getUserId().toString());
            UserEntity userEntity = result.getData();
            investmentDetails.setInvestmenterName(userEntity.getUsername());
            investmentDetails.setInvestmenterEmail(userEntity.getEmail());
            investmentDetails.setBankCardNumber(userEntity.getBankCardNumber());
            investmentDetails.setIdCardNumber(userEntity.getIdCardNumber());
            investmentDetails.setIdCardPngUp(userEntity.getIdCardPngUp());
            investmentDetails.setIdCardPngDown(userEntity.getIdCardPngDown());
            investmentDetailsDTOS.add(investmentDetails);
        }
        return investmentDetailsDTOS;
    }

    public List<InvestmentDetailsDTO> getInvestmentDetailsDTOByrefound(InvestmentDetailsDTO investmentDetailsDTO){

        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置格式
        String timeEnd=format.format(investmentDetailsDTO.getEndTime());
        String timeBegin=format.format(investmentDetailsDTO.getBeginTime());
        List<InvestmentDetailsDO> investmentDetailsDOS = null;
        if (investmentDetailsDTO.getProjectId() == null) {
            investmentDetailsDOS = investmentDetailsMapper.selectByRefund(timeEnd, timeBegin);
        }else{
            investmentDetailsDOS = investmentDetailsMapper.selectByRefundAndId(timeEnd,timeBegin,investmentDetailsDTO.getProjectId());
        }
        List<InvestmentDetailsDTO> investmentDetailsDTOS = new ArrayList<>();
        for (InvestmentDetailsDO investmentDetailsDO:investmentDetailsDOS){
            PayOrderDO payOrderDO = payOrderMapper.selectById(investmentDetailsDO.getId());
            if (payOrderDO==null){
                continue;
            }
            InvestmentDetailsDTO investmentDetails = new InvestmentDetailsDTO();
            investmentDetails.setProjectName(payOrderDO.getProjectName());
            investmentDetails.setProjectId(payOrderDO.getProjectId());
            investmentDetails.setPayOrderId(payOrderDO.getPayOrderId());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            investmentDetails.setPayTime(simpleDateFormat.format(payOrderDO.getGmtCreated()));
            Result<UserEntity> result = userClient.getUserById(payOrderDO.getUserId().toString());
            UserEntity userEntity = result.getData();
            investmentDetails.setInvestmenterName(userEntity.getUsername());
            investmentDetails.setInvestmenterEmail(userEntity.getEmail());
            investmentDetails.setBankCardNumber(userEntity.getBankCardNumber());
            investmentDetails.setIdCardNumber(userEntity.getIdCardNumber());
            investmentDetails.setIdCardPngUp(userEntity.getIdCardPngUp());
            investmentDetails.setIdCardPngDown(userEntity.getIdCardPngDown());
            investmentDetailsDTOS.add(investmentDetails);
        }
        return investmentDetailsDTOS;
    }

    public List<InvestmentDetailsDTO> getInvestmentDetailsDOSByuserId(Long userID){
        List<InvestmentDetailsDO> investmentDetailsDOS = investmentDetailsMapper.selectByPrimaryKey(userID);
        List<InvestmentDetailsDTO> dtos = BeanConverter.batchConvert(investmentDetailsDOS, InvestmentDetailsDTO.class);
        return dtos;
    }

    public InvestmentDetailsDO getInvestmentDetailsDOSById(Long id){
        InvestmentDetailsDO investmentDetailsDO = investmentDetailsMapper.selectById(id);
        return investmentDetailsDO;
    }

    public Long updateState(Long id,Integer state){
        Long aLong = investmentDetailsMapper.updateState(id, state);
        return aLong;
    }

    public Map<String,String> convertRequestParamsToMap(HttpServletRequest request){
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams  = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put(name, valueStr);
        }
        return params;
    }

    public void aliCheck(Map<String,String> params)throws AlipayApiException{
        String outTradeNo = params.get("out_trade_no");
        //商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号.
        PayOrderDO payOrderDO = payOrderMapper.selectByOrderId(outTradeNo);
        if (payOrderDO == null){
            throw new AlipayApiException("out_trade_no错误");
        }
        //判断total_amount是否确实为该订单的实际金额
        BigDecimal totalAmount=new BigDecimal(params.get("total_amount"));
        if (totalAmount.compareTo(payOrderDO.getInputMargin())!=0){
            throw new AlipayApiException("total_amount错误");
        }
        if (!params.get("app_id").equals(aliPayConfig.getAppid())){
            throw new AlipayApiException("app_id不一致");
        }
    }

    public boolean wxCheck(Map<String,String> params){
        String out_trade_no = params.get("out_trade_no");
        PayOrderDO payOrderDO = payOrderMapper.selectByOrderId(out_trade_no);
        if (payOrderDO == null){
            LOG.info("out_trade_no错误");
            return false;
        }
        //判断total_amount是否确实为该订单的实际金额
        BigDecimal totalAmount=new BigDecimal(params.get("total_fee")).divide(new BigDecimal(100),2,RoundingMode.HALF_UP);
        LOG.info("预下单金额:{}",payOrderDO.getInputMargin());
        LOG.info("处理前回传金额:{}",params.get("total_fee"));
        LOG.info("处理后回传金额:{}",totalAmount);
        if (totalAmount.compareTo(payOrderDO.getInputMargin())!=0){
            LOG.info("金额不一致");
            return false;
        }
        return true;

    }

    public boolean aliRefund(PayOrderDO payOrderDO){
        AlipayTradeRefundRequest alipayTradeRefundRequest = new AlipayTradeRefundRequest();
        AlipayRefund alipayRefund= new AlipayRefund();
        alipayRefund.setOut_trade_no(payOrderDO.getPayOrderId());
        alipayRefund.setTrade_no(payOrderDO.getTradeNo());
        alipayRefund.setRefund_amount(payOrderDO.getInputMargin().toString());
        alipayRefund.setRefund_reason("正常退款");
        alipayTradeRefundRequest.setBizContent(JSONObject.toJSONString(alipayRefund));
        try {
            AlipayTradeRefundResponse refundResponse = alipayClient.execute(alipayTradeRefundRequest);
            if (refundResponse.isSuccess()){
                LOG.info("退款成功");
                return true;
            }else{
                LOG.info("退款失败");
                return false;
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return true;
    }



    public AlipayNotifyParam buildAlipayNotifyParam(Map<String, String> params) {
        String json = JSON.toJSONString(params);
        return JSON.parseObject(json, AlipayNotifyParam.class);
    }

}
