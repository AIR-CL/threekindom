package com.fc.threekindom.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@Data
public class User implements Serializable {
    private Integer userId;
    private String userName;
    private String password;
    private String role;
    private String salt;
    private Integer integral;//积分
    private Integer userLevel;//等级
    private String mailbox;//邮箱
    private String signature;//个性签A名
    private String avatar;
    private Date createTime;//账号注册时间
    private Date signInTime;
    private Integer exp;


}
