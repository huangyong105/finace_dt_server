package com.jit.wxs.web;

import cn.com.taiji.DTO.ProjectManagementCopyDTO;
import cn.com.taiji.page.PageResult;
import com.jit.wxs.client.InvestmentClient;
import cn.com.taiji.DTO.ProjectManagementDTO;
import com.jit.wxs.entity.Result;
import com.jit.wxs.service.SysPermissionService;
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
    @Autowired
    SysPermissionService sysPermissionService;

    /**
     * 添加投资项目
     * @param req
     * @return
     */
    @RequestMapping(value = "/saveProject")
    public Result saveProject(@RequestBody ProjectManagementCopyDTO req){
        Result monthEarnResult = sysPermissionService.percentJudge(req.getMonthEarnings());
        if (!monthEarnResult.isSuccess()){
            return monthEarnResult;
        }

        Result expectedRequest = sysPermissionService.percentJudge(req.getExpectedRiskTolerance());
        if (!expectedRequest.isSuccess()){
            return expectedRequest;
        }
        ProjectManagementDTO projectManagementDTO = new ProjectManagementDTO();
        projectManagementDTO.setId(req.getId());
        projectManagementDTO.setProjectName(req.getProjectName());
        projectManagementDTO.setMonthEarnings(Integer.parseInt(req.getMonthEarnings()));
        projectManagementDTO.setExpectedRiskTolerance(Integer.parseInt(req.getExpectedRiskTolerance()));
        projectManagementDTO.setMinMargin(req.getMinMargin());
        projectManagementDTO.setMoneyProportion(req.getMoneyProportion());
        projectManagementDTO.setGmtCreated(req.getGmtCreated());
        projectManagementDTO.setGmtUpdated(req.getGmtUpdated());
        projectManagementDTO.setIsOnline(req.getIsOnline());
        projectManagementDTO.setCurrentPage(req.getCurrentPage());
        projectManagementDTO.setPageSize(req.getPageSize());
        Result result = investmentClient.saveProject(projectManagementDTO);
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
    public Result updateProject(@RequestBody ProjectManagementCopyDTO req){
        Result monthEarnResult = sysPermissionService.percentJudge(req.getMonthEarnings());
        if (!monthEarnResult.isSuccess()){
            return monthEarnResult;
        }

        Result expectedRequest = sysPermissionService.percentJudge(req.getExpectedRiskTolerance());
        if (!expectedRequest.isSuccess()){
            return expectedRequest;
        }
        ProjectManagementDTO projectManagementDTO = new ProjectManagementDTO();
        projectManagementDTO.setId(req.getId());
        projectManagementDTO.setProjectName(req.getProjectName());
        projectManagementDTO.setMonthEarnings(Integer.parseInt(req.getMonthEarnings()));
        projectManagementDTO.setExpectedRiskTolerance(Integer.parseInt(req.getExpectedRiskTolerance()));
        projectManagementDTO.setMinMargin(req.getMinMargin());
        projectManagementDTO.setMoneyProportion(req.getMoneyProportion());
        projectManagementDTO.setGmtCreated(req.getGmtCreated());
        projectManagementDTO.setGmtUpdated(req.getGmtUpdated());
        projectManagementDTO.setIsOnline(req.getIsOnline());
        projectManagementDTO.setCurrentPage(req.getCurrentPage());
        projectManagementDTO.setPageSize(req.getPageSize());
        investmentClient.updateProject(projectManagementDTO);
        return Result.ofSuccess();
    }



}
