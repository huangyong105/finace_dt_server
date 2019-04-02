package com.roncoo.eshop.web.controller;


import com.roncoo.eshop.DTO.ArticleManagementDTO;
import com.roncoo.eshop.manager.ArticleManager;
import com.roncoo.eshop.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<ArticleManagementDTO> allArticle = articleManager.getAllArticle();
        return Result.ofSuccess(allArticle);
    }
}
