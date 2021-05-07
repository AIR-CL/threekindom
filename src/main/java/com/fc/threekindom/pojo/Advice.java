package com.fc.threekindom.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@Data
public class Advice implements Serializable {
private Integer id;
private Integer userId;
private String userName;
private String advice;
private Date createTime;
private String reply;
private Integer readState;


}
