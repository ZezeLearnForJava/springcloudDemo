package com.zeze.api;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean reqResFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        ReqResFilter reqResFilter = new ReqResFilter();
        filterRegistrationBean.setFilter(reqResFilter);
        //filterRegistrationBean.addUrlPatterns("*.json");//配置过滤规则 　　　　
        return filterRegistrationBean;
    }
}