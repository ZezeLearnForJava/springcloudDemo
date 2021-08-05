package com.zeze.api;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.optionals.OptionalDecoder;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;
import io.opentracing.util.GlobalTracer;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


/**
 * 1
 */

public class FeignRequestInterceptor implements RequestInterceptor {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

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
                }
            });
        }
        System.out.println("api FeignRequest:");
        requestTemplate.header("test","apitest");
        Tracer tracer = GlobalTracer.get();
        //new TextMapAdapter()
        Span start = tracer.buildSpan(String.format("%s %s", requestTemplate.method(), requestTemplate.url())).start();
        tracer.activateSpan(start);
        Tags.COMPONENT.set(start,"java-openfeign");
    }

    @Bean
    public OptionalDecoder feignDecoder() {
        return new OptionalDecoder(new ResponseEntityDecoder(new TraceDecoder(this.messageConverters)));
    }
}


