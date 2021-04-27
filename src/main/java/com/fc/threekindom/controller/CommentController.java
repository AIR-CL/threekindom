package com.fc.threekindom.controller;

import com.fc.threekindom.pojo.Article;
import com.fc.threekindom.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/reply")
    @ResponseBody
    public Map<String,Object> reply(String content, String type ,HttpServletRequest request){
        Map<String, Object> map = commentService.reply(content,type,request);


        return map;
    }

}
