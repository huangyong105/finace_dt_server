package com.roncoo.eshop.web.controller;


import cn.com.taiji.data.Result;
import cn.com.taiji.data.User;
import cn.com.taiji.DTO.ArticleManagementDTO;
import cn.com.taiji.page.PageInfoDTO;
import cn.com.taiji.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.roncoo.eshop.client.UserClient;
import com.roncoo.eshop.manager.ArticleManager;
import cn.com.taiji.result.MyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    @Autowired
    private UserClient userClient;

    /**
     * 获取所有文章列表
     * @return
     */
    @RequestMapping("/getArticleList")
    public MyResult<List<ArticleManagementDTO>> getArticleList(@RequestHeader("token")String token){
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
        List<ArticleManagementDTO> list = articleManager.getArticleList();
        return MyResult.ofSuccess(list);
    }

    /**
     * 添加文章
     * todo 内部接口调用
     * @param articleManagementDTO
     * @return
     */
    @RequestMapping("/addArticleList")
    public MyResult addArticleList(@RequestBody ArticleManagementDTO articleManagementDTO){
        articleManager.addArticle(articleManagementDTO);
        return MyResult.ofSuccess();
    }

    /**
     * 获取所有文章（包含下架）
     * todo 内部接口调用
     * @return
     */
    @RequestMapping("/getAllArticle")
    public MyResult<PageResult<ArticleManagementDTO>> getAllArticle(@RequestBody ArticleManagementDTO articleManagementDTO){
        Integer currentPage = articleManagementDTO.getCurrentPage();
        Integer pageSize = articleManagementDTO.getPageSize();
        if (currentPage == null) {
            currentPage = 1;
        }
        if (pageSize == null) {
            pageSize = 100000;
        }
        PageResult<ArticleManagementDTO> pageResult = articleManager.getAllArticle(currentPage,pageSize);
        return MyResult.ofSuccess(pageResult);
    }

    /**
     * 设置上下架
     * todo 内部接口调用
     * @param articleManagementDTO
     * @return
     */
    @RequestMapping("/onlineOrOffline")
    public MyResult online(@RequestBody ArticleManagementDTO articleManagementDTO){
        articleManager.setLine(articleManagementDTO);
        return MyResult.ofSuccess();
    }

    /**
     * 更新文章
     * todo 内部调用接口
     * @param articleManagementDTO
     * @return
     */
    @RequestMapping("/updateArticle")
    public MyResult updateArticle(@RequestBody ArticleManagementDTO articleManagementDTO){
        articleManager.updateArticle(articleManagementDTO);
        return MyResult.ofSuccess();
    }

    /**
     * 获取文章详细信息
     * todo 内部调用接口
     * @param articleManagementDTO
     * @return
     */
    @RequestMapping("/getArticle")
    public MyResult<ArticleManagementDTO> getArticle(@RequestBody ArticleManagementDTO articleManagementDTO){
        ArticleManagementDTO res = articleManager.getArticle(articleManagementDTO);
        return MyResult.ofSuccess(res);
    }
}
