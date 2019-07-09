package com.jit.wxs.web;

import cn.com.taiji.page.PageResult;
import com.jit.wxs.client.InvestmentClient;
import cn.com.taiji.DTO.ProjectManagementDTO;
import cn.com.taiji.result.MyResult;
import com.jit.wxs.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:yong.huang
 * @Date: 2019/4/22 19:33
 */
@RestController
@RequestMapping(value = "/investment")
public class InvestmentController {

    @Autowired
    InvestmentClient investmentClient;

    /**
     * 添加投资项目
     * @param req
     * @return
     */
    @RequestMapping(value = "/saveProject")
    public Result saveProject(@RequestBody ProjectManagementDTO req){
        Result result = investmentClient.saveProject(req);
        return result;
    }

    /**
     * 获取全部项目列表（包括下架）
     * @return
     */
    @RequestMapping(value = "/getAllProjectList")
    public Result<PageResult<ProjectManagementDTO>> getAllProjectList(@RequestBody ProjectManagementDTO req){
        Result<PageResult<ProjectManagementDTO>> listMyResult = investmentClient.getAllProjectList(req);
        return listMyResult;
    }

    /**
     * 更新项目上线状态
     * @param req
     * @return
     */
    @RequestMapping(value = "/onlineOrOffline")
    public Result online(@RequestBody ProjectManagementDTO req){
        investmentClient.online(req);
        return Result.ofSuccess();
    }

    /**
     * 更新项目内容
     * @param req
     * @return
     */
    @RequestMapping(value = "/updateProject")
    public Result updateProject(@RequestBody ProjectManagementDTO req){
        investmentClient.updateProject(req);
        return Result.ofSuccess();
    }



}
