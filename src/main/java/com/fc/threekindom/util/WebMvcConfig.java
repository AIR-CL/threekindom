package com.fc.threekindom.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //相对路径映射 因为不重新部署，这个就会显示404
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //绝对路径映射 ，这个不重新部署就可以回显，因为映射你的本地
        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:\\idea\\threekindom\\src\\main\\resources\\static\\upload\\");
    }

}