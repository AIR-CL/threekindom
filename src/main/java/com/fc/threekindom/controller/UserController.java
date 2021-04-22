package com.fc.threekindom.controller;



import com.fc.threekindom.pojo.User;
import com.fc.threekindom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

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
    @GetMapping("/logout")
    protected String logout(HttpServletRequest req){
        HttpSession session = req.getSession();
        session.removeAttribute("username");
        session.removeAttribute("userId");
        return "index";

    }
    //个人中心界面退出登录
    @GetMapping("/ucLogout")
    protected String ucLogout(HttpServletRequest req){
        HttpSession session = req.getSession();
        session.removeAttribute("username");
        session.removeAttribute("userId");
        return "userCenter";

    }
    //头像修改退出登录
    @GetMapping("/avatarLogout")
    protected String avatarLogout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute("username");
        session.removeAttribute("userId");
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
    //跳转到主界面
    @GetMapping("/index")
    public String toIndexPage(){
        return "index";
    }
    //跳转到管理员界面
    @GetMapping("/admin")
    public String toAdminPage(){
        return "admin";
    }
    //跳转到用户中心
    @GetMapping("/userCenter")
    public String toUserCenterPage(Model model){
        model.addAttribute("set",3);
        return "userCenter";
    }
    //跳转到头像修改界面
    @GetMapping("/avatar")
    public String toAvatarPage(){
        return "avatar";
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

}
