package com.fc.threekindom.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Component
@Data
public class like implements Serializable {
    private Integer id;
    private Integer articleId;
    private Integer userId;
    private Integer likeType;
}
