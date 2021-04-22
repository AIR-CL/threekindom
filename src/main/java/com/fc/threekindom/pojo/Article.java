package com.fc.threekindom.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@Data
public class Article implements Serializable {
    private Integer articleId;
    private String articleTitle;
    private Integer createId;
    private String createName;
    private String articleContent;
    private String articleFace;
    private Integer likeCount;
    private Integer viewCount;
    private String tag;
    private String articleState;
    private Date modifyTime;
}
