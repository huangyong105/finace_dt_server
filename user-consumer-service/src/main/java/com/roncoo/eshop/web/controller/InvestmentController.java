package com.roncoo.eshop.web.controller;


import com.roncoo.eshop.DTO.ProjectManagementDTO;
import com.roncoo.eshop.manager.InvestmentManager;
import com.roncoo.eshop.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 投资相关接口
 *
 * @author dongyuan
 * @date 2019-03-22 11:39
 **/
@RestController
@RequestMapping("/investment")
public class InvestmentController {
    @Autowired
    private InvestmentManager investmentManager;

    /**
     * 获取全部项目列表
     * @return
     */
    @RequestMapping("/getProjectList")
    public Result<List<ProjectManagementDTO>> getProjectList(){
        List<ProjectManagementDTO> allInvestmentProject = investmentManager.getAllInvestmentProject();
        return Result.ofSuccess(allInvestmentProject);
    }

    /**
     * 根据id获取项目详情
     * @return
     */
    @RequestMapping("/getProject")
    public Result<ProjectManagementDTO> getProject(@RequestBody ProjectManagementDTO req){
        ProjectManagementDTO investmentProject = investmentManager.getInvestmentProject(req.getId());
        return Result.ofSuccess(investmentProject);
    }



}
