package com.zeze.client;

import com.zeze.api.Sayhello;
import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
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
    public String testSayHello(){
        return sayHello.sayHello();
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

