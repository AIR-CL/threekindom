package com.fc.threekindom;
import com.fc.threekindom.util.WebMvcConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(WebMvcConfig.class)
@SpringBootApplication
@MapperScan("com.fc.threekindom.mappers")
public class ThreekindomApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreekindomApplication .class, args);
    }

}