package com.zeze.api;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SayHelloController implements Sayhello{

    @Autowired
    private Tracer tracer;


    @GetMapping("/sayHello")
    @Override
    public String sayHello() {
        Span span = tracer.activeSpan();
        Span start = tracer.buildSpan("service").asChildOf(span).withTag("value", "zeze").start();

        try (Scope socpe = tracer.scopeManager().activate(start)){
            start.setTag("test", "test");
        } finally {
            start.finish();
        }

        return "zeze";
    }
}
