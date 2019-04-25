package com.roncoo.eshop.web.controller;


import cn.com.taiji.data.Result;
import cn.com.taiji.data.User;
import cn.com.taiji.DTO.ProjectManagementDTO;
import com.roncoo.eshop.client.UserClient;
import com.roncoo.eshop.manager.InvestmentManager;
import cn.com.taiji.result.MyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    @Autowired
    private UserClient userClient;

    /**
     * 获取全部项目列表
     * @return
     */
    @RequestMapping("/getProjectList")
    public MyResult<List<ProjectManagementDTO>> getProjectList(@RequestHeader("token")String token){
        Result<User> userResult = null;
        try {
            userResult = userClient.getUserInfo(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!userResult.isSuccess()||userResult.getData()==null)
        {
            return MyResult.ofError(4000,"未登陆");
        }
        List<ProjectManagementDTO> list = investmentManager.getInvestmentProjectList();
        return MyResult.ofSuccess(list);
    }

    /**
     * 根据id获取项目详情
     * @return
     */
    @RequestMapping("/getProject")
    public MyResult<ProjectManagementDTO> getProject(@RequestBody ProjectManagementDTO req,@RequestHeader("token")String token){
        Result<User> userResult = null;
        try {
            userResult = userClient.getUserInfo(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!userResult.isSuccess()||userResult.getData()==null)
        {
            return MyResult.ofError(4000,"未登陆");
        }
        ProjectManagementDTO investmentProject = investmentManager.getInvestmentProject(req.getId());
        return MyResult.ofSuccess(investmentProject);
    }

    /**
     * 添加投资项目
     * todo 内部调用
     * @param req
     * @return
     */
    @RequestMapping("/saveProject")
    public MyResult saveProject(@RequestBody ProjectManagementDTO req){
        investmentManager.saveInvestmentProject(req);
        return MyResult.ofSuccess();
    }

    /**
     * 获取全部项目列表（包括下架）
     * todo 内部调用
     * @return
     */
    @RequestMapping("/getAllProjectList")
    public MyResult<List<ProjectManagementDTO>> getAllProjectList(){
        List<ProjectManagementDTO> list = investmentManager.getAllInvestmentProject();
        return MyResult.ofSuccess(list);
    }

    /**
     * 更新项目上线状态
     * todo 内部调用
     * @param req
     * @return
     */
    @RequestMapping("/onlineOrOffline")
    public MyResult online(@RequestBody ProjectManagementDTO req){
        investmentManager.setLine(req);
        return MyResult.ofSuccess();
    }





}
