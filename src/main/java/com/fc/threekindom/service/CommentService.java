package com.fc.threekindom.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface CommentService {
    //回复
   Map<String,Object> reply(String content, String type , HttpServletRequest request);
}
