package com.fc.threekindom;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fc.threekindom.mappers")
public class ThreekindomApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreekindomApplication .class, args);
    }

}