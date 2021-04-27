package com.fc.threekindom.service.impl;

import com.fc.threekindom.mappers.ArticleMapper;
import com.fc.threekindom.mappers.CommentMapper;
import com.fc.threekindom.pojo.Article;
import com.fc.threekindom.pojo.Comment;
import com.fc.threekindom.pojo.like;
import com.fc.threekindom.service.ArticleService;
import com.fc.threekindom.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired(required = false)
    private ArticleMapper articleMapper;
    @Autowired(required = false)
    private CommentMapper commentMapper;


    //图片回显
    @Override
    public Map<String, Object> uploadPic(MultipartFile file, HttpServletRequest request) {
        Map<String,Object> map=new HashMap<>();
        //参数判断
        if (file==null||"".equals(file)){
            map.put("msg","请选择上传文件");
            return map;
        }
        //判断文件大小
        if (file.getSize()>52428800){
            map.put("state",100);
            map.put("msg","文件大小超出50M");
            return map;
        }
        //获取用户名
        String username =(String) request.getSession().getAttribute("username");
        System.out.println(username+12131);
        if ("".equals(username)||username==null){
            map.put("state",201);
            map.put("msg","用户名已过期,请重新登录");
            return map;
        }
//        //获取上传文件的原名
//        String filename = file.getOriginalFilename();
//        //从后往前找文件名的第一个"."
//      int start = filename.lastIndexOf(".");
        //得到文件的后缀
        String subffix = ".jpg";
        //filename.substring(start);
        //使用UUID产生文件名前缀
        String preffix = UUID.randomUUID().toString();
        //新的文件名
        String newFileName=preffix+subffix;
        //文件存储在哪里

        File f= UploadUtils.getArtDirFile();

        File filePath=new File(f.getAbsolutePath() +File.separator+newFileName);

        try {   //实现上传到服务器
            file.transferTo(filePath);

            //头像路径
            String face="/upload/article/"+newFileName;

//            int row = articleMapper.uploadPic(username, face);
//            if (row>=1){
                map.put("state","200");
                map.put("msg","上传成功");
                map.put("face",face);
                return map;
//            }else {
//                map.put("state","100");
//                map.put("msg","上传失败,请重试");
//                return map;
//            }
//

        } catch (IOException e) {
            e.printStackTrace();
            map.put("state","100");
            map.put("msg","上传失败");
            return map;
        }

    }

    //文章发布
    @Override
    public Map<String,Object> toPublishArticle(String topicId, String title, String content , String mainImg, String  category, String text,String userAvatar,HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        //通过用户名查找用户
        String username = (String)request.getSession().getAttribute("username");
        if (username==null){
            map.put("state",100);
            map.put("msg","用户名过期,请重新登录");
            return map;
        }
        Integer userId=(Integer)request.getSession().getAttribute("userId");
        Article article=new Article();
        article.setArticleTitle(title);
        article.setArticleContent(content);
        article.setArticleFace(mainImg);
        article.setTag(category);
        article.setCreateName(username);
        article.setCreateId(userId);
        article.setArticleState("已发布");
        article.setText(text);
        article.setCreateAvatar(userAvatar);
        int row=articleMapper.publishArticle(article);
        if (row>=1){
            map.put("state",200);
            map.put("msg","发布成功!!! ╮(￣▽ ￣)╭");
        }else {
            map.put("state",100);
            map.put("msg","发布失败! (×_×)");
        }

        return map;
    }

    //根据用户查询所有文章
    @Override
    public List<Article> searchAllArticleById( HttpServletRequest request){
        List list1=new ArrayList();
        String username = (String)request.getSession().getAttribute("username");
        if (username==null){
            list1.add(0,100);
            list1.add(1,"用户名过期,请重新登录");
            return list1;
        }
        Integer userId=(Integer)request.getSession().getAttribute("userId");
        List<Article> list = articleMapper.searchAllArticleById(userId);
        request.getSession().setAttribute("userArticle",list);
        return list;


    }

    //删除文章
    @Override
    public Map<String,Object> deleteArticle(String articleId){
        int id= Integer.parseInt(articleId);
        Map<String,Object> map=new HashMap<>();
        int row=articleMapper.deleteArticle(id);

        if (row>=1){
            map.put("state",200);
            map.put("msg","删除成功!!! ╮(￣▽ ￣)╭");
        }else {
            map.put("state",100);
            map.put("msg","删除失败! (×_×)");
        }
        return map;
    }

    //查询所有文章
    @Override
    public List<Article> searchAllArticle(String tag){

        List<Article> list = articleMapper.searchAllArticle();
        List<Article> listZaTn=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getTag().equals(tag)){
                        listZaTn.add(list.get(i));
            }
           if (listZaTn.size()>2){
               i=list.size();
           }
        }
        return listZaTn;


    }
//通过文章id查找文章
    @Override
   public Map<String,Object> findArticleById(String articleId, HttpServletRequest request, Model model){
        int id= Integer.parseInt(articleId);
        Map<String,Object> map=new HashMap<>();
        Article article=articleMapper.findArticleById(id);
        Integer createId=article.getCreateId();
        List<Article> list=articleMapper.findArticleByCreateId(createId);
        request.getSession().setAttribute("createArticleNum",list.size());
        int viewAll=0;
        int likeAll=0;
        for (int i=0;i<list.size();i++){
            viewAll=viewAll+list.get(i).getViewCount();
            likeAll=likeAll+list.get(i).getLikeCount();
        }
        request.getSession().setAttribute("viewAll",viewAll);
        request.getSession().setAttribute("likeAll",likeAll);

        List<Comment> comment=commentMapper.findCommentById(id);
        if (article==null){
            map.put("state",100);
        }else {
            int i=articleMapper.readAdd(id);
            if (request.getSession().getAttribute("userId")!=null) {


                like like = articleMapper.likeState(Integer.parseInt(articleId), Integer.parseInt(request.getSession().getAttribute("userId").toString()));
                if (like == null) {
                    request.getSession().setAttribute("likeType", 1);
                } else {
                    request.getSession().setAttribute("likeType", like.getLikeType());
                }
            }else{
                request.getSession().setAttribute("likeType", 1);
            }
            map.put("state",200);
            request.getSession().setAttribute("clickArticle",article);
            request.getSession().setAttribute("clickArticleComment",comment);
            model.addAttribute("content",article.getArticleContent());
            request.getSession().setAttribute("commentCount",comment.size());


        }
        return map;
    }
//刷新文章界面
@Override
public Map<String,Object> reload(String articleId, HttpServletRequest request, Model model){
    int id= Integer.parseInt(articleId);
    Map<String,Object> map=new HashMap<>();
    Article article=articleMapper.findArticleById(id);
    List<Comment> comment=commentMapper.findCommentById(id);
    if (article==null){
        map.put("state",100);
    }else {
        int i=articleMapper.readAdd(id);
        System.out.println(i+"+阅读数");
        like like = articleMapper.likeState(Integer.parseInt(articleId), Integer.parseInt(request.getSession().getAttribute("userId").toString()));
        if (like==null){
            request.getSession().setAttribute("likeType",1);
        }else{
            request.getSession().setAttribute("likeType",like.getLikeType());
        }

        map.put("state",200);

        request.getSession().setAttribute("clickArticle",article);
        request.getSession().setAttribute("clickArticleComment",comment);
        model.addAttribute("content",article.getArticleContent());
        request.getSession().setAttribute("commentCount",comment.size());



    }
    return map;
}
//点赞
    @Override
    public   Map<String,Object> like(String articleId,String likeType,HttpServletRequest request){
        like like =new like();
        Map<String,Object> map=new HashMap<>();
        like = articleMapper.likeState(Integer.parseInt(articleId), Integer.parseInt(request.getSession().getAttribute("userId").toString()));
        if (like==null){
            like like1=new like();
            like1.setUserId(Integer.parseInt(request.getSession().getAttribute("userId").toString()));
            like1.setArticleId(Integer.parseInt(articleId));
            like1.setLikeType(Integer.parseInt(likeType));
            articleMapper.addLikeCount(Integer.parseInt(articleId));
            System.out.println("222222");
            int row = articleMapper.like(like1);
            if (row>=1){
                map.put("state",100);
            }else {
                map.put("state", 200);
            }
            return map;
        }else {
            if (likeType.equals("2")){
                articleMapper.addLikeCount(Integer.parseInt(articleId));
                System.out.println("222222");

            }else{
                articleMapper.decLikeCount(Integer.parseInt(articleId));
                System.out.println("111111111");
            }
            int row = articleMapper.modifyLike(Integer.parseInt(likeType), Integer.parseInt(articleId), Integer.parseInt(request.getSession().getAttribute("userId").toString()));
            if (row>=1){
                map.put("state",100);
            }else {
                map.put("state", 200);
            }
            return map;
        }




    }
//去文章总览
    @Override
    public void toOverView(Model model,HttpServletRequest request){
        int createId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        String tag1="学术";
        String tag2="资讯";
        String tag3="杂谈";
        List<Article> list1=articleMapper.findArticleByCreateIdAndTag(createId,tag1);
        request.getSession().setAttribute("xArticleNum",list1.size());
        int viewAll=0;
        int likeAll=0;
        for (int i=0;i<list1.size();i++){
            viewAll=viewAll+list1.get(i).getViewCount();
            likeAll=likeAll+list1.get(i).getLikeCount();
        }
        request.getSession().setAttribute("xViewAll",viewAll);
        request.getSession().setAttribute("xLikeAll",likeAll);

        List<Article> list2=articleMapper.findArticleByCreateIdAndTag(createId,tag2);
        request.getSession().setAttribute("zArticleNum",list2.size());
        int zviewAll=0;
        int zlikeAll=0;
        for (int i=0;i<list2.size();i++){
            zviewAll=zviewAll+list2.get(i).getViewCount();
            zlikeAll=zlikeAll+list2.get(i).getLikeCount();
        }
        request.getSession().setAttribute("zViewAll",zviewAll);
        request.getSession().setAttribute("zLikeAll",zlikeAll);

        List<Article> list3=articleMapper.findArticleByCreateIdAndTag(createId,tag3);
        request.getSession().setAttribute("tArticleNum",list3.size());
        int tviewAll=0;
        int tlikeAll=0;
        for (int i=0;i<list3.size();i++){
            tviewAll=tviewAll+list3.get(i).getViewCount();
            tlikeAll=tlikeAll+list3.get(i).getLikeCount();
        }
        request.getSession().setAttribute("tViewAll",tviewAll);
        request.getSession().setAttribute("tLikeAll",tlikeAll);
    }
}
