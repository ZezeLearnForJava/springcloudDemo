package com.zeze.api;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Optional;


/**
 *
 */
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            Optional.ofNullable(headerNames).ifPresent(headers -> {
                while (headers.hasMoreElements()) {
                    String name = headers.nextElement();
                    String value = request.getHeader(name);
                    requestTemplate.header(name, value);
                    System.err.println(name + ":" +  value);
                }
            });
        }
    }

}


