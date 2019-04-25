package com.jit.wxs.web;


import cn.com.taiji.DTO.InvestmentDetailsDTO;
import cn.com.taiji.result.MyResult;
import com.github.pagehelper.PageHelper;
import com.jit.wxs.Enum.StateEnum;
import com.jit.wxs.client.InvestmentClient;
import com.jit.wxs.entity.CUser;
import com.jit.wxs.entity.Page;
import com.jit.wxs.entity.Result;
import com.jit.wxs.page.PageInfoDTO;
import com.jit.wxs.page.PageResult;
import com.jit.wxs.service.CuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        PageInfoDTO pageInfo = new PageInfoDTO(currentPage, pageSize);
        pageInfo.setPageInfoData(userList);
        PageResult<CUser> pageResult = new PageResult<>(userList, pageInfo);
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
     * @param id
     * @return
     */
    @RequestMapping("/getMyInvestment")
    public Result getMyInvestment(Long id){
        Result<InvestmentDetailsDTO> myInvestment = investmentClient.getMyInvestment(id);
        return Result.ofSuccess(myInvestment);
    }


}
