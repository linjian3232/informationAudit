package com.javaclimb.music.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SingerPicConfig implements WebMvcConfigurer {

    @Override
    public  void addResourceHandlers(ResourceHandlerRegistry registy){
       registy.addResourceHandler("/img/singerPic/**").addResourceLocations("file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"img"+System.getProperty("file.separator")+"singerPic"+System.getProperty("file.separator"));
    }
}
