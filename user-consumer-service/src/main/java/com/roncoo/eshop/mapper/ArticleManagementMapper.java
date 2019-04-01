package com.roncoo.eshop.mapper;


import com.roncoo.eshop.model.ArticleManagementDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ArticleManagementMapper {

    @Select("select * from article_management where is_online = 1 and deleted = 0")
    List<ArticleManagementDO> selectAllArticle();

}