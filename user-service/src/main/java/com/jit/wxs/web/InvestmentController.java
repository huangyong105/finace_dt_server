package com.jit.wxs.web;

import cn.com.taiji.page.PageResult;
import cn.com.taiji.result.Result;
import com.jit.wxs.client.InvestmentClient;
import cn.com.taiji.DTO.ProjectManagementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
