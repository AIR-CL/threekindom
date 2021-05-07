package com.fc.threekindom.service;

import com.fc.threekindom.pojo.Article;
import com.fc.threekindom.pojo.User;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ArticleService {
    //上传图片
    Map<String,Object> uploadPic(MultipartFile file, HttpServletRequest request);
    //发布文章
    Map<String,Object> toPublishArticle(String topicId, String title, String content , String mainImg, String  category, String text,String userAvatar,HttpServletRequest request);
    //发布公告
    Map<String,Object> toPublishNotice(String topicId, String title, String content , String mainImg, String  category, String text,String userAvatar,HttpServletRequest request);
    //根据用户查账所有文章
    List<Article> searchAllArticleById( HttpServletRequest request);
    //用户删除文章
    Map<String,Object> deleteArticle(String articleId);
    //查询所有文章
    List<Article> searchAllArticle( String tag);
    //通过文章id查询文章
    Map<String,Object> findArticleById(String articleId, HttpServletRequest request, Model model);
    //评论后刷新文章界面
    Map<String,Object> reload(String articleId, HttpServletRequest request, Model model);
    //点赞
    Map<String,Object> like(String articleId,String likeType,HttpServletRequest request);
    //去文章总览
    void toOverView(Model model,HttpServletRequest request);
    //去更多界面
    Map<String,Object> toMore(String tag,HttpServletRequest request);
    /**
     * 查询所有文章
     * @return
     */
    public List<Article> getAll();
}
