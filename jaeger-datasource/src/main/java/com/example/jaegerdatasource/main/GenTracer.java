package com.example.jaegerdatasource.main;

import io.jaegertracing.internal.JaegerSpan;
import io.jaegertracing.internal.JaegerTracer;

import java.util.Collections;
import java.util.HashMap;

import static sun.jvm.hotspot.code.CompressedStream.L;

public class GenTracer {

    public static JaegerTracer initTracer() {
        io.jaegertracing.Configuration config = new io.jaegertracing.Configuration("client");
        io.jaegertracing.Configuration.SenderConfiguration sender = new io.jaegertracing.Configuration.SenderConfiguration().withAgentHost("42.193.97.239");
        //sender.withEndpoint("");
        config.withSampler(new io.jaegertracing.Configuration.SamplerConfiguration().withType("const").withParam(1));
        config.withReporter(new io.jaegertracing.Configuration.ReporterConfiguration().withSender(sender).withMaxQueueSize(10000));
        return config.getTracer();
    }

    public static void main(String[] args) {
        JaegerTracer tracer = initTracer();

        JaegerSpan test = tracer.buildSpan("test").start();
        test.log("even");
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("one","string");
        stringObjectHashMap.put("two", 123);
        stringObjectHashMap.put("there", 123.123);
        test.log(stringObjectHashMap);
        test.log(System.currentTimeMillis()/1000, "test");

        test.finish();
    }
}
