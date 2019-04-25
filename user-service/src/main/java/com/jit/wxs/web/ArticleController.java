package com.jit.wxs.web;


import cn.com.taiji.DTO.ArticleManagementDTO;


import com.jit.wxs.client.InvestmentClient;
import com.jit.wxs.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文章接口
 *
 * @author dongyuan
 * @date 2019-03-22 16:54
 **/
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    InvestmentClient investmentClient;
    /**
     * 添加文章
     * @param articleManagementDTO
     * @return
     */
    @RequestMapping("/addArticleList")
    public Result addArticleList(@RequestBody ArticleManagementDTO articleManagementDTO){
        Result result = investmentClient.addArticleList(articleManagementDTO);
        return result;
    }

    /**
     * 获取所有文章（包含下架）
     * @return
     */
    @RequestMapping("/getAllArticle")
    public Result<List<ArticleManagementDTO>> getAllArticle(){
        Result<List<ArticleManagementDTO>> allArticle = investmentClient.getAllArticle();
        return allArticle;
    }

    /**
     * 设置上下架
     * @param articleManagementDTO
     * @return
     */
    @RequestMapping("/onlineOrOffline")
    public Result online(@RequestBody ArticleManagementDTO articleManagementDTO){
        Result online = investmentClient.online(articleManagementDTO);
        return online;
    }

    /**
     * 更新文章
     * @param articleManagementDTO
     * @return
     */
    @RequestMapping("/updateArticle")
    public Result updateArticle(@RequestBody ArticleManagementDTO articleManagementDTO){
        Result result = investmentClient.updateArticle(articleManagementDTO);
        return result;
    }

    /**
     * 获取文章详细信息
     * @param articleManagementDTO
     * @return
     */
    @RequestMapping("/getArticle")
    public Result<ArticleManagementDTO> getArticle(@RequestBody ArticleManagementDTO articleManagementDTO){
        Result<ArticleManagementDTO> article = investmentClient.getArticle(articleManagementDTO);
        return article;
    }
}
