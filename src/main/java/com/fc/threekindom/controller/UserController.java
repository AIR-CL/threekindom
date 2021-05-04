package com.fc.threekindom.controller;



import com.fc.threekindom.mappers.ArticleMapper;
import com.fc.threekindom.mappers.UserMapper;
import com.fc.threekindom.pojo.Article;
import com.fc.threekindom.pojo.User;
import com.fc.threekindom.service.ArticleService;
import com.fc.threekindom.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired(required = false)
    private ArticleMapper articleMapper;
    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/find")
    @ResponseBody
    public List<User> findAll(){
        List<User> list=userService.findAll();
        return list;
    }
    //用户中心设置小界面
    @GetMapping("/setting")
    public String setting(Model model){
    model.addAttribute("set",1);
        return "userCenter";
    }
    //用户中心修改密码小界面
    @GetMapping("/modifyPwd")
    public String modifyPwd(Model model){
        model.addAttribute("set",2);
        return "userCenter";
    }
    //用户中心动态小界面
    @GetMapping("/dongTai")
    public String dongTai(Model model){
        model.addAttribute("set",3);
        return "userCenter";
    }
    //主界面退出登录
    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest req){
        HttpSession session = req.getSession();
        session.removeAttribute("username");
        session.removeAttribute("userId");
        session.removeAttribute("signIn");
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("index");

            List<Article> list=articleService.searchAllArticle("杂谈");
            modelAndView.addObject("zaTan",list);
            List<Article> list1=articleService.searchAllArticle("盘点");
            modelAndView.addObject("panDian",list1);
        List<Article> list2=articleService.searchAllArticle("学术");
        modelAndView.addObject("xueShu",list2);

            return modelAndView;
    }
    //个人中心界面退出登录
    @GetMapping("/ucLogout")
  public  String ucLogout(HttpServletRequest req){
        HttpSession session = req.getSession();
        session.removeAttribute("username");
        session.removeAttribute("userId");
        session.removeAttribute("signIn");
        return "userCenter";

    }
    //头像修改退出登录
    @GetMapping("/avatarLogout")
    public String avatarLogout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute("username");
        session.removeAttribute("userId");
        session.removeAttribute("signIn");
        return "avatar";
    }


    //跳转到登录界面
    @GetMapping("/log")
    public String tologin(){
    return "login";
    }
    //跳转到注册界面
    @GetMapping("/reg")
    public String toRegPage(){
        return "register";
    }
    //去主界面
    @RequestMapping("/index")
    public ModelAndView toIndexPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        List<Article> list=articleService.searchAllArticle("杂谈");
        modelAndView.addObject("zaTan",list);
        List<Article> list1=articleService.searchAllArticle("资讯");
        modelAndView.addObject("ziXun",list1);
        List<Article> list2=articleService.searchAllArticle("学术");
        modelAndView.addObject("xueShu",list2);
        return modelAndView;

    }
    //更多文章界面
    @RequestMapping("/more")
    public ModelAndView toMorePage(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("moreArticle");
        String tag = request.getSession().getAttribute("tag").toString();
        List<Article> articleByTagComment = articleMapper.findArticleByTagComment(tag);
        List<Article> articleByLike = articleMapper.findArticleByLike(tag);
        List<Article> articleByView = articleMapper.findArticleByView(tag);
        List<Article> articleByTagTime = articleMapper.findArticleByTagTime(tag);
        modelAndView.addObject("tagListTime",articleByTagTime);
        modelAndView.addObject("tagListComment",articleByTagComment);
        modelAndView.addObject("likeList",articleByLike);
        modelAndView.addObject("viewList",articleByView);
        return modelAndView;
    }


    //跳转到管理员界面
    @GetMapping("/admin")
    public String toAdminPage(){
        return "admin";
    }
    //跳转到用户中心
    @GetMapping("/userCenter")
    public String toUserCenterPage(Model model,HttpServletRequest request){
        model.addAttribute("set",3);
        userService.toUserCenter(model,request);
        return "userCenter";
    }
    //跳转到头像修改界面
    @GetMapping("/avatar")
    public String toAvatarPage(){
        return "avatar";
    }
    //跳转到文章界面
    @GetMapping("/article")
    public String toArticlePage(){
        return "article";
    }

    @PostMapping("/checkUsername")
    @ResponseBody//将返回值转成json数据
    public Map<String,Object> checkUsername(String username){
        System.out.println(username);
        //使用业务层对象调用业务层方法
        Map<String, Object> map = userService.checkUserName(username);
        return map;
    }
    //处理注册
    @PostMapping("/toReg")
    @ResponseBody//将返回值转成json数据
    public Map<String,Object> toReg(String username,String password){
        Map<String, Object> map = userService.toReg(username, password);
        return map;
    }

    //处理登陆
    @PostMapping("/toLog")
    @ResponseBody
    public Map<String,Object> toLog(String username, String password, HttpServletRequest req){
        Map<String, Object> map = userService.toLog(username, password,req);
        return map;
    }
    //更新密码
    @PostMapping("/toUpdatePwd")
    @ResponseBody
    public Map<String,Object> toUpdatePwd(String oldPassword,String newPassword,String confirmPassword,HttpServletRequest req){
        Map<String, Object> map = userService.toUpdatePassword(oldPassword, newPassword, confirmPassword, req);
        return map;
    }
    //头像上传
    @PostMapping("/uploadAvatar")
    @ResponseBody
    public Map<String,Object>toUpload(MultipartFile file, HttpServletRequest request){
        Map<String, Object> map = userService.uploadFace(file, request);
        return map;
    }
    //根据用户名修改信息
    @PostMapping("/modifyUserInfoByUsername")
    @ResponseBody
    public  Map<String,Object> modifyUserInfoByName(String userName, String mailbox,String signature,HttpServletRequest req) {
        Map<String, Object> map = userService.modifyUserByName(userName,mailbox, signature,req);
        return map;
    }
    //签到
    @PostMapping("/signIn")
    @ResponseBody
    public  Map<String,Object> signIn(HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        Integer id=Integer.parseInt(request.getSession().getAttribute("userId").toString());

        int integral=10;
        int exp=100;
        int i = userMapper.signIn(id,integral,exp);
        if (i>=1){
            request.getSession().setAttribute("signIn","yes");
            map.put("state",200);
            return map;
        }else {
            map.put("state",100);
            request.getSession().setAttribute("signIn","no");
            return map;

        }
    }

   //分页查询数据
    @GetMapping("/userManage")
    public String usermanage(Model model,
                             @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                             @RequestParam(defaultValue="8",value="pageSize")Integer pageSize){

        //为了程序的严谨性，判断非空：
        if(pageNum == null){
            pageNum = 1;   //设置默认当前页
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 8;    //设置默认每页显示的数据数
        }
        System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);

        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum,pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<User> userList = userService.getAll();
            System.out.println("分页数据："+userList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<User> pageInfo = new PageInfo<User>(userList,pageSize);
            System.out.println(pageInfo);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo",pageInfo);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        //5.设置返回的jsp/html等前端页面
        // thymeleaf默认就会拼串classpath:/templates/xxxx.html
        return "userManage";
    }

    //根据id查用户信息
    @PostMapping("/findUserById")
    @ResponseBody
    public Map<String,Object> findUserById(String userId){
        Integer id1=Integer.parseInt(userId);
        User userMo = userMapper.findUserById(id1);
        Map<String,Object> map=new HashMap<>();
        map.put("userMo",userMo);
        map.put("state",200);
        return map;
    }
    //后台修改用户信息
    @PostMapping("/modifyUserInfo")
    @ResponseBody
    public Map<String,Object> modifyUserInfo(String userName,String integral,String exp){
        User user=new User();
        user.setUserName(userName);
        user.setExp(Integer.parseInt(exp));
        user.setIntegral(Integer.parseInt(integral));
        System.out.println(user);
        Map<String,Object> map=new HashMap<>();
        int i = userMapper.modifyUserInfo(user);
        if (i>=1){
            map.put("state",200);
            map.put("msg","修改成功");
            return map;

        }else{
            map.put("state",100);
            map.put("msg","修改失败");
            return map;
        }
    }
    @PostMapping("/deleteUserById")
    @ResponseBody
    public Map<String,Object> deleteUserById(String userId){
        Integer id1=Integer.parseInt(userId);
        Map<String,Object> map=new HashMap<>();
        int i = userMapper.deleteUser(id1);
        if (i>=1){
            map.put("state",200);
            map.put("msg","删除成功");
            return map;

        }else{
            map.put("state",100);
            map.put("msg","删除失败");
            return map;
        }
    }
}
