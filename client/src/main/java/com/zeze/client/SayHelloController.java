package com.zeze.client;

import com.zeze.api.Sayhello;
import feign.*;
import feign.opentracing.FeignSpanDecorator;
import feign.opentracing.TracingClient;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static java.util.concurrent.TimeUnit.SECONDS;

@RestController
@RequiredArgsConstructor
public class SayHelloController {



    private final Sayhello sayHello;



    public interface sayHelloApi {

        @Headers("Content-Type: application/json")
        @RequestLine("GET /sayHello")
        String sayHello();

    }

    @Autowired
    private Tracer tracer;


    @RequestMapping("sayHello")
    public String testSayHello(){

    /*  return    Feign.builder()
                //.client(new TracingClient(new OkHttpClient(),GlobalTracer.get()))
                .target(sayHelloApi.class, "http://localhost:12080").sayHello();*/
        String s = sayHello.sayHello();
        return s;
    }

    @GetMapping("/testJaeger")
    public String testJaeger() {
        Span span = tracer.buildSpan("parentSpan").withTag("myTag", "spanFirst").start();
        tracer.scopeManager().activate(span);
        tracer.activeSpan().setTag("methodName", "testTracing");
        span.finish();
        return "jaeger";
    }
}

