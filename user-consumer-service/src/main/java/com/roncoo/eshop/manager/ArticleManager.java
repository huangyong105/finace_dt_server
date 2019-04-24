package com.roncoo.eshop.manager;


import cn.com.taiji.DTO.ArticleManagementDTO;
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

    public List<ArticleManagementDTO> getAllArticle(){
        List<ArticleManagementDO> list = articleManagementMapper.getAllArticle();
        List<ArticleManagementDTO> articleManagementDTOS = BeanConverter.batchConvert(list, ArticleManagementDTO.class);
        return articleManagementDTOS;
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
