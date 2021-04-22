package com.fc.threekindom.controller;

import com.fc.threekindom.pojo.Article;
import com.fc.threekindom.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @PostMapping("/upload")
    @ResponseBody
    public Map<String,Object> toUpload(MultipartFile file, HttpServletRequest request){
        Map<String, Object> map = articleService.uploadPic(file, request);
        return map;
    }
    @PostMapping("/publish")
    @ResponseBody
    public Map<String,Object> toPublish( String topicId, String title, String content , String mainImg, String  category,HttpServletRequest request){
        Map<String, Object> map = articleService.toPublishArticle(topicId,title,content,mainImg,category,request);
        return map;
    }
    //查询所有文章
    @PostMapping("/searchAllArticle")
    @ResponseBody
    public List<Article> searchAllArticleById(HttpServletRequest request){
        List<Article> list=articleService.searchAllArticleById(request);
    return list;
    }
    //用户删除文章
    @PostMapping("/delete")
    @ResponseBody
    public Map<String,Object> deleteArticle(String articleId){
        System.out.println(articleId);
        Map<String,Object> map=articleService.deleteArticle(articleId);
        return  map;
    }
}
