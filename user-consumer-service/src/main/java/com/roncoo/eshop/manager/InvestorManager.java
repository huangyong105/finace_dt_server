package com.roncoo.eshop.manager;


import cn.com.taiji.DTO.InvestmentDetailsDTO;
import cn.com.taiji.page.PageInfo;
import cn.com.taiji.page.PageResult;
import com.alipay.api.AlipayApiException;
import com.github.pagehelper.PageHelper;
import com.roncoo.eshop.converter.BeanConverter;
import com.roncoo.eshop.mapper.InvestmentDetailsMapper;
import com.roncoo.eshop.mapper.PayOrderMapper;
import com.roncoo.eshop.model.InvestmentDetailsDO;
import com.roncoo.eshop.model.PayOrderDO;
import com.roncoo.eshop.util.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author dongyuan
 * @date 2019-03-24 22:45
 **/
@Service
public class InvestorManager {
    @Autowired
    FtpUtil ftpUtil;
    @Autowired
    InvestmentDetailsMapper investmentDetailsMapper;
    @Autowired
    PayOrderMapper payOrderMapper;

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

    public List<InvestmentDetailsDTO> getInvestmentDetailsDOSByuserId(Long userID){
        List<InvestmentDetailsDO> investmentDetailsDOS = investmentDetailsMapper.selectByPrimaryKey(userID);
        List<InvestmentDetailsDTO> dtos = BeanConverter.batchConvert(investmentDetailsDOS, InvestmentDetailsDTO.class);
        return dtos;
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

    public void check(Map<String,String> params)throws AlipayApiException{
        String outTradeNo = params.get("out_trade_no");
        //商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号.
        PayOrderDO payOrderDO = payOrderMapper.selectByOrderId(outTradeNo);
        if (payOrderDO == null){
            throw new AlipayApiException("out_trade_no错误");
        }
        //判断total_amount是否确实为该订单的实际金额
        new BigDecimal(params.get("total_amount")).multiply(new BigDecimal(100));
    }


}
