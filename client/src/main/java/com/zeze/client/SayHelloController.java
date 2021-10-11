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

@RestController
@RequiredArgsConstructor
public class SayHelloController {


    private final Sayhello sayHello;

    @Autowired
    private Tracer tracer;


    @RequestMapping("sayHello")
    public String testSayHello() {
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

