package com.roncoo.eshop.manager;


import com.roncoo.eshop.DTO.ArticleManagementDTO;
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

    public List<ArticleManagementDTO> getAllArticle(){
        List<ArticleManagementDO> list = articleManagementMapper.selectAllArticle();
        List<ArticleManagementDTO> articleManagementDTOS = BeanConverter.batchConvert(list, ArticleManagementDTO.class);
        return articleManagementDTOS;
    }
}
