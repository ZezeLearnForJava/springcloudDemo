package com.zeze.springmvcgetallurl.mvc;

import com.zeze.springmvcgetallurl.utils.TracerUrlMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 自定义拦截器
 */
@Component
public class TracerInterceptor implements HandlerInterceptor {


    @Autowired
    TracerUrlMapping tracerUrlMapping;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String> urlMapping = tracerUrlMapping.getUrlMapping();


        // eg: GET /api/test01
        String key = request.getMethod() + " " + request.getServletPath();
        String chineseChar = urlMapping.get(key);
        System.out.println();

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
