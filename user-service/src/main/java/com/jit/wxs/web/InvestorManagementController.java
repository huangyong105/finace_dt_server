package com.jit.wxs.web;

import com.jit.wxs.client.InvestmentClient;
import com.roncoo.eshop.DTO.ProjectManagementDTO;
import com.roncoo.eshop.result.MyResult;
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
    public MyResult<List<ProjectManagementDTO>> getProjectList(){
        MyResult<List<ProjectManagementDTO>> list = investmentClient.getProjectList();
        return list;
    }

    /**
     * 根据id获取项目详情
     * @return
     */
    @RequestMapping("/getProject")
    public MyResult<ProjectManagementDTO> getProject(@RequestBody ProjectManagementDTO req){
        MyResult<ProjectManagementDTO> project = investmentClient.getProject(req);
        return project;
    }

    /**
     * 添加投资项目
     * todo 内部调用
     * @param req
     * @return
     */
    @RequestMapping("/saveProject")
    public MyResult saveProject(@RequestBody ProjectManagementDTO req){
        MyResult result = investmentClient.saveProject(req);
        return result;
    }

    /**
     * 获取全部项目列表（包括下架）
     * todo 内部调用
     * @return
     */
    @RequestMapping("/getAllProjectList")
    public MyResult<List<ProjectManagementDTO>> getAllProjectList(){
        MyResult<List<ProjectManagementDTO>> listMyResult = investmentClient.getAllProjectList();
        return listMyResult;
    }

    /**
     * 更新项目上线状态
     * todo 内部调用
     * @param req
     * @return
     */
    @RequestMapping("/onlineOrOffline")
    public MyResult online(@RequestBody ProjectManagementDTO req){
        investmentClient.online(req);
        return MyResult.ofSuccess();
    }

}
