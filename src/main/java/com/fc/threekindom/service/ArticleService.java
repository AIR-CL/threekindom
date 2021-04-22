package com.fc.threekindom.service;

import com.fc.threekindom.pojo.Article;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ArticleService {
    //上传图片
    Map<String,Object> uploadPic(MultipartFile file, HttpServletRequest request);
    //发布文章
    Map<String,Object> toPublishArticle(String topicId, String title, String content , String mainImg, String  category,HttpServletRequest request);
    //根据用户查账所有文章
    List<Article> searchAllArticleById( HttpServletRequest request);
    //用户删除文章
    Map<String,Object> deleteArticle(String articleId);
}
