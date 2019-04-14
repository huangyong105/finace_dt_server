package com.roncoo.eshop.mapper;


import com.roncoo.eshop.model.ArticleManagementDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ArticleManagementMapper {

    @Select("select * from article_management where is_online = 1 and deleted = 0")
    List<ArticleManagementDO> getArticleList();

    @Select("select * from article_management where deleted = 0")
    List<ArticleManagementDO> getAllArticle();

    @Select("select * from article_management where id=#{id}")
    ArticleManagementDO getArticle(Long id);

    @Insert("insert into article_management" +
            "(article_name,article_desc,link,gmt_created,gmt_updated,is_online,deleted)" +
            "values (#{articleName},#{articleDesc},#{link},now(),now(),1,0)")
    void insertArticle(ArticleManagementDO articleManagementDO);

    @Update("update article_management set is_online=#{isOnline} where id=#{id}")
    void updateline(ArticleManagementDO articleManagementDO);

    @Update("update article_management set article_name=#{articleName},article_desc=#{articleDesc},link=#{link} where id=#{id}")
    void updateArticle(ArticleManagementDO articleManagementDO);

}