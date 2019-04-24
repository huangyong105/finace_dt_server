package com.jit.wxs.web;

import com.jit.wxs.client.InvestmentClient;
import cn.com.taiji.DTO.ProjectManagementDTO;
import com.jit.wxs.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:yong.huang
 * @Date: 2019/4/22 19:33
 */
@Controller
@RequestMapping(value = "/investment")
public class InvestorManagementController {

    @Autowired
    InvestmentClient investmentClient;
    /**
     * 获取全部项目列表
     * @return
     */
    @RequestMapping("/getProjectList")
    public Result<List<ProjectManagementDTO>> getProjectList(){
        Result<List<ProjectManagementDTO>> list = investmentClient.getProjectList();
        return list;
    }

    /**
     * 根据id获取项目详情
     * @return
     */
    @RequestMapping("/getProject")
    public Result<ProjectManagementDTO> getProject(@RequestBody ProjectManagementDTO req){
        Result<ProjectManagementDTO> project = investmentClient.getProject(req);
        return project;
    }

    /**
     * 添加投资项目
     * todo 内部调用
     * @param req
     * @return
     */
    @RequestMapping("/saveProject")
    public Result saveProject(@RequestBody ProjectManagementDTO req){
        Result result = investmentClient.saveProject(req);
        return result;
    }

    /**
     * 获取全部项目列表（包括下架）
     * todo 内部调用
     * @return
     */
    @RequestMapping("/getAllProjectList")
    public Result<List<ProjectManagementDTO>> getAllProjectList(){
        Result<List<ProjectManagementDTO>> listMyResult = investmentClient.getAllProjectList();
        return listMyResult;
    }

    /**
     * 更新项目上线状态
     * todo 内部调用
     * @param req
     * @return
     */
    @RequestMapping("/onlineOrOffline")
    public Result online(@RequestBody ProjectManagementDTO req){
        investmentClient.online(req);
        return Result.ofSuccess();
    }

}
