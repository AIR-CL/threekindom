package com.fc.threekindom.service.impl;

import com.fc.threekindom.mappers.ArticleMapper;
import com.fc.threekindom.pojo.Article;
import com.fc.threekindom.service.ArticleService;
import com.fc.threekindom.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired(required = false)
    private ArticleMapper articleMapper;


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
                request.getSession().setAttribute("avatar",face);
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
    public Map<String,Object> toPublishArticle(String topicId, String title, String content , String mainImg, String  category,HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        //通过用户名查找用户
        String username = (String)request.getSession().getAttribute("username");
        if (username==null){
            map.put("state",200);
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

}
