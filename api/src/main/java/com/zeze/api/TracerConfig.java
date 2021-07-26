package com.zeze.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracerConfig {
    @Bean
    public io.opentracing.Tracer tracer() {
        io.jaegertracing.Configuration config = new io.jaegertracing.Configuration("api");
        io.jaegertracing.Configuration.SenderConfiguration sender = new io.jaegertracing.Configuration.SenderConfiguration().withAgentHost("zeze.com");
        config.withSampler(new io.jaegertracing.Configuration.SamplerConfiguration().withType("const").withParam(1));
        config.withReporter(new io.jaegertracing.Configuration.ReporterConfiguration().withSender(sender).withMaxQueueSize(10000));
        return config.getTracer();
    }
}
