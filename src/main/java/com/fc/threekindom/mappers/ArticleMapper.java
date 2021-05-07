package com.fc.threekindom.mappers;

import com.fc.threekindom.pojo.Article;
import com.fc.threekindom.pojo.User;
import com.fc.threekindom.pojo.like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Mapper
public interface ArticleMapper {
    //发布文章
    int publishArticle(Article article);
    //修改公告
    int publishNotice(Article article);
    //通过用户名查找他的文章
    List<Article> searchAllArticleById(int userId);
    //通过文章id删除文章
    int deleteArticle(int id);
    //查找所有文章
    List<Article> searchAllArticle();
    //通过文章id找文章
    Article findArticleById(int articleId);
    //阅读数
    int readAdd(int articleId);
    //点赞
    int like(like like);
    //通过id查找点赞状态;
    like likeState(Integer articleId,Integer userId);
    //修改点赞状态
    int modifyLike(Integer likeType,Integer articleId,Integer userId);
    //根据文章查询所有点赞记录
    List<like> likeCount(Integer articleId,Integer likeType);
    //增加点赞数
    int addLikeCount(Integer articleId);
    //减少点赞数
    int decLikeCount(Integer articleId);
    //根据文章创建者id查找文章
    List<Article> findArticleByCreateId(Integer createId);
    //根据标签和id找文章
    List<Article> findArticleByCreateIdAndTag(Integer createId,String tag);
    //通过标签查找文章时间排序
    List<Article> findArticleByTagTime(String tag);
    //通过标签查找文章评论数排序
    List<Article> findArticleByTagComment(String tag);
    //根据阅读量查找所有文章
    List<Article> findArticleByView(String tag);
    //根据点赞数排序
    List<Article> findArticleByLike(String tag);
    //修改评论数
    int modifyCommentCount(Integer commentCount,Integer articleId);
    //查询莫一天文章数
    List<Article> findAllByModifyTime(Integer i);
    //后台查询所有文章
    List<Article> getAll();
    //根据文章id修改文章
    int modifyArticle(Article article);
    //查询公告
    Article findNotice();
}
