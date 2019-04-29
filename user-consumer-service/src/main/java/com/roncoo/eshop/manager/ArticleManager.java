package com.roncoo.eshop.manager;


import cn.com.taiji.DTO.ArticleManagementDTO;
import cn.com.taiji.page.PageInfo;
import cn.com.taiji.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.roncoo.eshop.converter.BeanConverter;
import com.roncoo.eshop.mapper.ArticleManagementMapper;
import com.roncoo.eshop.model.ArticleManagementDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dongyuan
 * @date 2019-03-22 16:55
 **/
@Service
public class ArticleManager {
    @Autowired
    ArticleManagementMapper articleManagementMapper;

    public List<ArticleManagementDTO> getArticleList(){
        List<ArticleManagementDO> list = articleManagementMapper.getArticleList();
        List<ArticleManagementDTO> articleManagementDTOS = BeanConverter.batchConvert(list, ArticleManagementDTO.class);
        return articleManagementDTOS;
    }

    public PageResult<ArticleManagementDTO> getAllArticle(Integer currentPage,Integer pageSize){

        PageHelper.startPage(currentPage, pageSize);
        List<ArticleManagementDO> list = articleManagementMapper.getAllArticle();
        com.github.pagehelper.PageInfo pageInfo = new com.github.pagehelper.PageInfo(list);
        List<ArticleManagementDTO> articleManagementDTOS = BeanConverter.batchConvert(list, ArticleManagementDTO.class);
        PageInfo pageInfo1 = new PageInfo();
        pageInfo1.setCurrentPage(currentPage);
        pageInfo1.setPageSize(pageSize);
        pageInfo1.setTotalCount(pageInfo.getTotal());
        pageInfo1.setTotalPage(pageInfo.getPages());
        PageResult<ArticleManagementDTO> pageResult = new PageResult<>(articleManagementDTOS, pageInfo1);
        return pageResult;
    }

    public ArticleManagementDTO getArticle(ArticleManagementDTO articleManagementDTO){
        ArticleManagementDO articleManagementDO = articleManagementMapper.getArticle(articleManagementDTO.getId());
        ArticleManagementDTO res = BeanConverter.convert(articleManagementDO, ArticleManagementDTO.class);
        return res;
    }

    public void addArticle(ArticleManagementDTO articleManagementDTO){
        ArticleManagementDO articleManagementDO = BeanConverter.convert(articleManagementDTO, ArticleManagementDO.class);
        articleManagementMapper.insertArticle(articleManagementDO);
    }

    public void setLine(ArticleManagementDTO articleManagementDTO){
        ArticleManagementDO articleManagementDO = BeanConverter.convert(articleManagementDTO, ArticleManagementDO.class);
        articleManagementMapper.updateline(articleManagementDO);
    }

    public void updateArticle(ArticleManagementDTO articleManagementDTO){
        ArticleManagementDO articleManagementDO = BeanConverter.convert(articleManagementDTO, ArticleManagementDO.class);
        articleManagementMapper.updateArticle(articleManagementDO);
    }
}
