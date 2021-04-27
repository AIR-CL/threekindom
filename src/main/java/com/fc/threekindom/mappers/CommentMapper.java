package com.fc.threekindom.mappers;

import com.fc.threekindom.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
int insertComment(Comment comment);
List<Comment> findCommentById(Integer articleId);
}
