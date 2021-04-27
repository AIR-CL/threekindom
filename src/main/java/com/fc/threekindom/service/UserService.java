package com.fc.threekindom.service;

import com.fc.threekindom.pojo.User;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserService {
 List<User> findAll();
 //根据用户名查询用户信息
 Map<String,Object> checkUserName(String username);
 //注册
 Map<String,Object> toReg(String username,String password);
 //根据用户名和密码查询用户信息
 Map<String, Object> toLog(String username, String password, HttpServletRequest req);
 //根据用户名修改密码
 Map<String,Object> toUpdatePassword(String oldPassword,String newPassword,String confirmPassword,HttpServletRequest request);
 //上传头像
 Map<String,Object> uploadFace(MultipartFile file, HttpServletRequest request);
 //根据用户名修改密码
 Map<String,Object> modifyUserByName(String userName,String mailbox,String signature,HttpServletRequest req);
 //去用户中心
 void toUserCenter(Model model, HttpServletRequest request);
}
