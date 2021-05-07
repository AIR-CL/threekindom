package com.fc.threekindom.controller;

import com.fc.threekindom.mappers.ArticleMapper;
import com.fc.threekindom.pojo.Article;
import com.fc.threekindom.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired(required = false)
    private ArticleMapper articleMapper;

    @PostMapping("/upload")
    @ResponseBody
    public Map<String,Object> toUpload(MultipartFile file, HttpServletRequest request){
        Map<String, Object> map = articleService.uploadPic(file, request);
        return map;
    }
    @PostMapping("/publish")
    @ResponseBody
    public Map<String,Object> toPublish( String topicId, String title, String content , String mainImg, String  category, String text,String userAvatar,HttpServletRequest request){
        Map<String, Object> map = articleService.toPublishArticle(topicId,title,content,mainImg,category,text,userAvatar,request);
        return map;
    }
    @PostMapping("/pubNotice")
    @ResponseBody
    public Map<String,Object> toNotice( String topicId, String title, String content , String mainImg, String  category, String text,String userAvatar,HttpServletRequest request){
        Map<String, Object> map = articleService.toPublishNotice(topicId,title,content,mainImg,category,text,userAvatar,request);
        return map;
    }

    //用户删除文章
    @PostMapping("/delete")
    @ResponseBody
    public Map<String,Object> deleteArticle(String articleId){

        Map<String,Object> map=articleService.deleteArticle(articleId);
        return  map;
    }



    //去文章页
    @PostMapping("/article")
    @ResponseBody
    public Map<String,Object>toArticlePage(String articleId, HttpServletRequest request, Model model){
        Map<String,Object> map=articleService.findArticleById(articleId,request,model);
        if (articleId==null){
            map.put("state",100);
            return map;
        }
        return  map;
    }

    //评论后刷新文章界面
    @PostMapping("/reload")
    @ResponseBody
    public Map<String,Object>toPage(String articleId, HttpServletRequest request, Model model){
        Map<String,Object> map=articleService.reload(articleId,request,model);
        if (articleId==null){
            map.put("state",100);
            return map;
        }
        return  map;
    }

//点赞
    @PostMapping("/like")
    @ResponseBody
    public Map<String,Object> like(String articleId,String likeType,HttpServletRequest request){
        Map<String,Object> map=articleService.like(articleId,likeType,request);
        return map;

    }
    //根据标签查找文章
    @PostMapping("/more")
    @ResponseBody
    public Map<String,Object> toMore(String tag,HttpServletRequest request){
        Map<String,Object> map=articleService.toMore(tag,request);
        return map;

    }
    @GetMapping("/notice")
    public String toNoticePage( HttpServletRequest request){
        List<Article> notice=articleService.searchAllArticle("公告");
         request.getSession().setAttribute("notice",notice);
        return "notice";
    }

}
