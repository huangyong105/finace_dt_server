package com.jit.wxs.client;

import cn.com.taiji.DTO.ArticleManagementDTO;
import cn.com.taiji.DTO.InvestmentDetailsDTO;
import cn.com.taiji.DTO.ProjectManagementDTO;
import com.jit.wxs.entity.Result;
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
     * 添加投资项目
     * todo 内部调用
     * @param req
     * @return
     */
    @RequestMapping("/investment/saveProject")
    public Result saveProject(@RequestBody ProjectManagementDTO req);
    /**
     * 获取全部项目列表（包括下架）
     * todo 内部调用
     * @return
     */
    @RequestMapping("/investment/getAllProjectList")
    public Result<List<ProjectManagementDTO>> getAllProjectList();

    /**
     * 更新项目上线状态
     * todo 内部调用
     * @param req
     * @return
     */
    @RequestMapping("/investment/onlineOrOffline")
    public Result online(@RequestBody ProjectManagementDTO req);




    /**
     * 添加文章
     * todo 内部接口调用
     * @param articleManagementDTO
     * @return
     */
    @RequestMapping("/article/addArticleList")
    public Result addArticleList(@RequestBody ArticleManagementDTO articleManagementDTO);

    /**
     * 获取所有文章（包含下架）
     * todo 内部接口调用
     * @return
     */
    @RequestMapping("/article/getAllArticle")
    public Result<List<ArticleManagementDTO>> getAllArticle();

    /**
     * 设置上下架
     * todo 内部接口调用
     * @param articleManagementDTO
     * @return
     */
    @RequestMapping("/article/onlineOrOffline")
    public Result online(@RequestBody ArticleManagementDTO articleManagementDTO);

    /**
     * 更新文章
     * todo 内部调用接口
     * @param articleManagementDTO
     * @return
     */
    @RequestMapping("/article/updateArticle")
    public Result updateArticle(@RequestBody ArticleManagementDTO articleManagementDTO);

    /**
     * 获取文章详细信息
     * todo 内部调用接口
     * @param articleManagementDTO
     * @return
     */
    @RequestMapping("/article/getArticle")
    public Result<ArticleManagementDTO> getArticle(@RequestBody ArticleManagementDTO articleManagementDTO);


    /**
     * 获取用户投资详情
     * @return
     */
    @RequestMapping("/investor/getMyInvestment")
    public Result<InvestmentDetailsDTO> getMyInvestment(Long id);
}

