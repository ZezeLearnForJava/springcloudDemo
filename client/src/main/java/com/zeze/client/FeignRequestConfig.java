package com.zeze.client;


import feign.RequestInterceptor;

import feign.RequestTemplate;
import feign.optionals.OptionalDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


/**
 * 传递head
 */
@Configuration
public class FeignRequestConfig implements RequestInterceptor {
    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;



    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String values = request.getHeader(name);
                template.header(name, values);
            }
        }
        //template.header("test","test");
        Enumeration<String> bodyNames = request.getParameterNames();
        StringBuffer body = new StringBuffer();
        if (bodyNames != null) {
            while (bodyNames.hasMoreElements()) {
                String name = bodyNames.nextElement();
                String values = request.getParameter(name);
                body.append(name).append("=").append(values).append("&");
            }
        }

        if (body.length() != 0) {
            body.deleteCharAt(body.length() - 1);
            template.body(body.toString());
        }
    }


    @Bean
    public OptionalDecoder feignDecoder() {
        return new OptionalDecoder(new ResponseEntityDecoder(new TraceDecoder(this.messageConverters)));
    }
}

