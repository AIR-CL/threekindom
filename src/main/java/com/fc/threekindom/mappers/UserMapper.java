package com.fc.threekindom.mappers;

import com.fc.threekindom.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper

public interface UserMapper {
    List<User> find();
    //根据用户名查询当前用户信息
    User findUserByUserName(String username);
    //添加新用户
    int regUser(User user);
    //根据用户名和密码查询用户信息
    User loginCheck(@Param("username") String username, @Param("password") String password);
    //根据用户名修改密码
    int toUpdatePassword(@Param("newPassword") String newPassword,@Param("username") String username,@Param("newSalt") String newSalt);
    //上传头像
    int uploadFace(@Param("username") String username,@Param("face") String face);
    //根据用户名修改信息
    int modifyUserInfoByUsername(User user);
    //签到
    int signIn(@Param("userId") Integer userId,@Param("integral") Integer integral,@Param("exp") Integer exp);
    //修改登录时间
    int setLogTime(@Param("userId") Integer userId);
    //查询过去某一天的所有数据
    List<User> findAllByLogTime(Integer i);
    //查询所有用户
//    @Select("SELECT * FROM tb_user")
//    @ResultMap(value = "userMap")
  List<User> getAll();
    //根据用户id查找用户
    User findUserById(Integer userId);
    //管理员修改用户信息
    int modifyUserInfo(User user);
    //后台根据用户id删除用户
    int deleteUser(Integer userId);
}
