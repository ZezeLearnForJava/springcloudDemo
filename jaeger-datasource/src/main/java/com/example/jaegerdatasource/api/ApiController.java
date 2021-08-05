package com.example.jaegerdatasource.api;

import com.example.jaegerdatasource.model.Span;
import io.jaegertracing.internal.JaegerSpan;
import io.jaegertracing.internal.JaegerTracer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/api/traces")
    public Span tracer( Long end,
                       int limit,
                        String lookback,
                        String maxDuration,
                        String minDuration,
                        String service,
                      // @RequestParam long start
    ) {

        Span span = new Span();
        span.setSpanID("1231312321");
        span.setTraceID("12312312312312");
        span.setFlags(1);
        span.setOperationName("test");
        return span;
    }

}
