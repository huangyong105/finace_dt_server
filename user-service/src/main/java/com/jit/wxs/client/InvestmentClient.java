package com.jit.wxs.client;

import com.roncoo.eshop.DTO.ProjectManagementDTO;
import com.roncoo.eshop.result.MyResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:yong.huang
 * @Date: 2019/4/10 22:04
 */

@FeignClient(value = "user-consumer-service",url="http://47.101.147.30:8772/")
public interface InvestmentClient {


    /**
     * 获取全部项目列表
     * @return
     */
    @RequestMapping("/getProjectList")
    public MyResult<List<ProjectManagementDTO>> getProjectList();

    /**
     * 根据id获取项目详情
     * @return
     */
    @RequestMapping("/getProject")
    public MyResult<ProjectManagementDTO> getProject(@RequestBody ProjectManagementDTO req);

    /**
     * 添加投资项目
     * todo 内部调用
     * @param req
     * @return
     */
    @RequestMapping("/saveProject")
    public MyResult saveProject(@RequestBody ProjectManagementDTO req);
    /**
     * 获取全部项目列表（包括下架）
     * todo 内部调用
     * @return
     */
    @RequestMapping("/getAllProjectList")
    public MyResult<List<ProjectManagementDTO>> getAllProjectList();

    /**
     * 更新项目上线状态
     * todo 内部调用
     * @param req
     * @return
     */
    @RequestMapping("/onlineOrOffline")
    public MyResult online(@RequestBody ProjectManagementDTO req);
}

