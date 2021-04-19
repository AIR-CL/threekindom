package com.fc.threekindom;

import com.fc.threekindom.mappers.UserMapper;
import com.fc.threekindom.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ThreekindomApplicationTests {
    @Autowired(required = false)
    UserMapper userMapper;
    @Test
    public void t1(){
        List<User> list =userMapper.find();
        for (User u:list){
            System.out.println(u.toString());
        }
    }

    @Test
    void contextLoads() {
    }

}
