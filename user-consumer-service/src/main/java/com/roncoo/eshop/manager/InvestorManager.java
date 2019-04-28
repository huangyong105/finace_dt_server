package com.roncoo.eshop.manager;


import cn.com.taiji.DTO.InvestmentDetailsDTO;
import cn.com.taiji.page.PageInfo;
import cn.com.taiji.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.roncoo.eshop.converter.BeanConverter;
import com.roncoo.eshop.mapper.InvestmentDetailsMapper;
import com.roncoo.eshop.model.InvestmentDetailsDO;
import com.roncoo.eshop.util.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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



}
