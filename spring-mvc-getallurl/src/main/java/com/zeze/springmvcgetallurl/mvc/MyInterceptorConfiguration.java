package com.zeze.springmvcgetallurl.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyInterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    TracerInterceptor tracerInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tracerInterceptor).addPathPatterns("/**");
    }
}
