package com.fc.threekindom.service.impl;

import com.fc.threekindom.mappers.CommentMapper;
import com.fc.threekindom.pojo.Article;
import com.fc.threekindom.pojo.Comment;
import com.fc.threekindom.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired(required = false)
    CommentMapper commentMapper;

    //回复
    @Override
    public Map<String, Object> reply(String content, String type , HttpServletRequest request){
        String usename=request.getSession().getAttribute("username").toString();
        String avatar=request.getSession().getAttribute("avatar").toString();
      Article article =(Article)request.getSession().getAttribute("clickArticle");
        Integer articleId=article.getArticleId();

        Map<String,Object> map=new HashMap<>();
        Comment comment=new Comment();
       comment.setCommentator(usename);
       comment.setCommentFace(avatar);
       comment.setCommentContent(content);
       comment.setState(1);
       comment.setArticleId(articleId);

        int row=commentMapper.insertComment(comment);
        if (row>=1){
            map.put("state",200);
            map.put("msg","成功");

            return map;

        }else{
            map.put("state",100);
            map.put("msg","失败");
            return map;
        }




    }

}
