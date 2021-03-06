package com.roncoo.eshop.web.controller;


import com.roncoo.eshop.DTO.ArticleManagementDTO;
import com.roncoo.eshop.manager.ArticleManager;
import com.roncoo.eshop.model.ArticleManagementDO;
import com.roncoo.eshop.result.Result;
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
    private ArticleManager articleManager;

    /**
     * 获取所有文章列表
     * @return
     */
    @RequestMapping("/getArticleList")
    public Result<List<ArticleManagementDTO>> getArticleList(){
        List<ArticleManagementDTO> list = articleManager.getArticleList();
        return Result.ofSuccess(list);
    }

    /**
     * 添加文章
     * todo 内部接口调用
     * @param articleManagementDTO
     * @return
     */
    @RequestMapping("/addArticleList")
    public Result addArticleList(@RequestBody ArticleManagementDTO articleManagementDTO){
        articleManager.addArticle(articleManagementDTO);
        return Result.ofSuccess();
    }

    /**
     * 获取所有文章（包含下架）
     * todo 内部接口调用
     * @return
     */
    @RequestMapping("/getAllArticle")
    public Result<List<ArticleManagementDTO>> getAllArticle(){
        List<ArticleManagementDTO> list = articleManager.getAllArticle();
        return Result.ofSuccess(list);
    }

    /**
     * 设置上下架
     * todo 内部接口调用
     * @param articleManagementDTO
     * @return
     */
    @RequestMapping("/onlineOrOffline")
    public Result online(@RequestBody ArticleManagementDTO articleManagementDTO){
        articleManager.setLine(articleManagementDTO);
        return Result.ofSuccess();
    }

    /**
     * 更新文章
     * todo 内部调用接口
     * @param articleManagementDTO
     * @return
     */
    @RequestMapping("/updateArticle")
    public Result updateArticle(@RequestBody ArticleManagementDTO articleManagementDTO){
        articleManager.updateArticle(articleManagementDTO);
        return Result.ofSuccess();
    }

    /**
     * 获取文章详细信息
     * todo 内部调用接口
     * @param articleManagementDTO
     * @return
     */
    @RequestMapping("/getArticle")
    public Result<ArticleManagementDTO> getArticle(@RequestBody ArticleManagementDTO articleManagementDTO){
        ArticleManagementDTO res = articleManager.getArticle(articleManagementDTO);
        return Result.ofSuccess(res);
    }
}
