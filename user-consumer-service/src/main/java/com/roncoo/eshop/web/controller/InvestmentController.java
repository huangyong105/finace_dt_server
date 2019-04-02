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
        List<ProjectManagementDTO> list = investmentManager.getInvestmentProjectList();
        return Result.ofSuccess(list);
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

    /**
     * 添加投资项目
     * todo 内部调用
     * @param req
     * @return
     */
    @RequestMapping("/saveProject")
    public Result saveProject(@RequestBody ProjectManagementDTO req){
        investmentManager.saveInvestmentProject(req);
        return Result.ofSuccess();
    }

    /**
     * 获取全部项目列表（包括下架）
     * todo 内部调用
     * @return
     */
    @RequestMapping("getAllProjectList")
    public Result<List<ProjectManagementDTO>> getAllProjectList(){
        List<ProjectManagementDTO> list = investmentManager.getAllInvestmentProject();
        return Result.ofSuccess(list);
    }

    /**
     * 更新项目上线状态
     * todo 内部调用
     * @param req
     * @return
     */
    @RequestMapping("onlineOrOffline")
    public Result online(@RequestBody ProjectManagementDTO req){
        investmentManager.setLine(req);
        return Result.ofSuccess();
    }


}
