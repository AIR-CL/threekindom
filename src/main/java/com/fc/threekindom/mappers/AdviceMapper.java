package com.fc.threekindom.mappers;

import com.fc.threekindom.pojo.Advice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface AdviceMapper {
    //提建议
    int giveAdvice(Advice advice);
    //查找所有建议
    List<Advice> findAllAdvice();
    //根据id查建议
    Advice findAdviceById(Integer id);
    //后台回复建议
    int replyAdvice(@Param("id") Integer id,@Param("reply") String reply);
//根据用户名更改阅读状态为已读
    int changeReadState(String username);
    //根据用户名查询所有留言
    List<Advice> findAllAdviceByName(String username);
}
