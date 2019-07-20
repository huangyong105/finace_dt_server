package com.jit.wxs.web;


import cn.com.taiji.DTO.InvestmentDetailsDTO;
import cn.com.taiji.page.PageInfo;
import cn.com.taiji.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.jit.wxs.Enum.StateEnum;
import com.jit.wxs.client.InvestmentClient;
import com.jit.wxs.entity.CUser;
import com.jit.wxs.entity.Result;
import com.jit.wxs.service.CuserService;
import com.jit.wxs.util.POIUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 用户相关接口
 *
 * @author dongyuan
 * @date 2019-03-23 15:10
 **/
@RestController
@RequestMapping("/investor")
public class InvestorManagementController {
    @Autowired
    CuserService cuserService;
    @Autowired
    InvestmentClient investmentClient;

    /**
     * 获取c端用户列表
     * @param user
     * @return
     */
    @RequestMapping("/getUserList")
    public Result<PageResult<CUser>> getUserList(@RequestBody CUser user){
        Integer currentPage = user.getCurrentPage();
        Integer pageSize = user.getPageSize();
        if (currentPage == null) {
            currentPage = 1;
        }
        if (pageSize == null) {
            pageSize = 100000;
        }
        PageHelper.startPage(currentPage, pageSize);
        List<CUser> userList = cuserService.getUserList(user);
        com.github.pagehelper.PageInfo pageInfo = new com.github.pagehelper.PageInfo(userList);
        PageInfo pageInfo1 = new PageInfo();
        pageInfo1.setCurrentPage(currentPage);
        pageInfo1.setPageSize(pageSize);
        pageInfo1.setTotalCount(pageInfo.getTotal());
        pageInfo1.setTotalPage(pageInfo.getPages());
        PageResult<CUser> pageResult = new PageResult<>(userList, pageInfo1);
        return Result.ofSuccess(pageResult);
    }

    /**
     * 获取c端用户详情
     * @param user
     * @return
     */
    @RequestMapping("/getUserInfo")
    public Result<CUser> getUserInfo(@RequestBody CUser user){
        CUser userInfo = cuserService.getUserInfo(user);
        Integer state = userInfo.getState();
        String stateDesc = StateEnum.getValueByKey(state);
        userInfo.setStateDesc(stateDesc);
        return Result.ofSuccess(userInfo);
    }

    /**
     * 根据c端用户id获取投资项目列表
     * @param
     * @return
     */
    @RequestMapping("/getMyInvestment")
    public Result<PageResult<InvestmentDetailsDTO>> getMyInvestment(@RequestBody InvestmentDetailsDTO investmentDetailsDTO){
        Result<PageResult<InvestmentDetailsDTO>> myInvestment = investmentClient.getMyInvestment(investmentDetailsDTO);
        PageResult<InvestmentDetailsDTO> value = myInvestment.getValue();
        for (InvestmentDetailsDTO dto:value.getData()){
            //dto.setStateDesc(PayStateEnum.getValueByKey(dto.getState()));
            if(dto.getState()==1){
                dto.setStateDesc("正常");
            }
            if (dto.getState()==2){
                dto.setStateDesc("已退款");
            }
            if (dto.getState()==3){
                dto.setStateDesc("申请退款");
            }
        }

        return Result.ofSuccess(value);
    }


    @RequestMapping("/refunded")
    public Result refunded(@RequestBody InvestmentDetailsDTO investmentDetailsDTO){
        Result refunded = investmentClient.refunded(investmentDetailsDTO);
        if (refunded.isSuccess()){
            return Result.ofSuccess();
        }
        return Result.ofError(5000,refunded.getMessage());
    }

    @RequestMapping("/exportMyInvestment")
    public void  exportMyInvestment(@RequestBody InvestmentDetailsDTO investmentDetailsDTO, HttpServletResponse response){
        investmentDetailsDTO.setCurrentPage(1);
        investmentDetailsDTO.setPageSize(1000);
        Result<PageResult<InvestmentDetailsDTO>> myInvestment = investmentClient.getMyInvestment(investmentDetailsDTO);
        PageResult<InvestmentDetailsDTO> value = myInvestment.getValue();
        List<InvestmentDetailsDTO> investmentDetailsDTOS = value.getData();
        for (InvestmentDetailsDTO dto:investmentDetailsDTOS){
            //dto.setStateDesc(PayStateEnum.getValueByKey(dto.getState()));
            if(dto.getState()==1){
                dto.setStateDesc("正常");
            }
            if (dto.getState()==2){
                dto.setStateDesc("已退款");
            }
            if (dto.getState()==3){
                dto.setStateDesc("申请退款");
            }
        }

        POIUtils<InvestmentDetailsDTO,Object> ex = new POIUtils<InvestmentDetailsDTO,Object>();
        String[] headers = new String[]{"主键自增id","项目ID","投资人id","项目名称","魔柜名称","所属店铺","魔柜类型","补货前商品数","上货商品数","下货商品数","补货后商品数"};
        String fileName = "项目管理_"+DateUtils.formatDate(new Date(),"yyyyMMdd");
        ex.exportToExcel(response,fileName,headers, investmentDetailsDTOS);
        return ;
    }

}
