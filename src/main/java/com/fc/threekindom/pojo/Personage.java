package com.fc.threekindom.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
public class Personage implements Serializable {
    private Integer id;
    private String name;
    private String nativePlace;
    private  String life;
    private  String personImage;
    private String nation;

}
