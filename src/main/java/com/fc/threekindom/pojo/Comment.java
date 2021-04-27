package com.fc.threekindom.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@Data
public class Comment implements Serializable {
    private Integer commentId;
    private Integer parentId;
    private String commentator;
    private Date createTime;
    private String commentContent;
    private Integer articleId;
    private Integer state;
    private String commentFace;
}
