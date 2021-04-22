package com.fc.threekindom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/publish")
public class PublishController {
//去文章总览界面
    @GetMapping("/overview")
    public String toOverviewPage(){
        return "overview";
    }
    //去撰写专栏界面
    @GetMapping("/column")
    public String toColumnPage(){
        return "column";
    }
    //去文章总览界面
    @GetMapping("/board")
    public String toBoardPage(){
        return "articleBoard";
    }
    //去ceshi
    @GetMapping("/text")
    public String textPage(){
        return "text";
    }
}
