package com.fc.threekindom.controller;

import com.fc.threekindom.pojo.Article;
import com.fc.threekindom.service.ArticleService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/publish")
public class PublishController {
    @Autowired
    private  ArticleService articleService;

//去文章总览界面
    @GetMapping("/overview")
    public String toOverviewPage(Model model,HttpServletRequest request){
        articleService.toOverView(model,request);

        return "overview";
    }
    //去撰写专栏界面
    @GetMapping("/column")
    public String toColumnPage(){
        return "column";
    }
    //去文章版
    @RequestMapping("/board")
    public ModelAndView code(HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("articleBoard");
        String username = (String)request.getSession().getAttribute("username");
        if (username==null){
            return modelAndView;
        }
        List<Article> list=articleService.searchAllArticleById(request);
        modelAndView.addObject("article",list);

        return modelAndView;
    }

    //去文章总览界面
    @GetMapping("/article")
    public String toArticlePage(){

        return "article";
    }

    //去ceshi
    @GetMapping("/text")
    public String textPage(){
        return "text";
    }

}
