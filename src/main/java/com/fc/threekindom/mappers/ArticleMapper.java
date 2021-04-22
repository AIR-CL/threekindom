package com.fc.threekindom.mappers;

import com.fc.threekindom.pojo.Article;
import com.fc.threekindom.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {
    //添加新用户
    int publishArticle(Article article);
    List<Article> searchAllArticleById(int userId);
    int deleteArticle(int id);
}
