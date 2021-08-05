package com.zeze.api;

import feign.FeignException;
import feign.Response;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;
import io.opentracing.util.GlobalTracer;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * 自定义解码器负责，解码response
 */
class TraceDecoder extends SpringDecoder {

    TraceDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        super(messageConverters);
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {


        System.out.println("api Decoder:");
        // 这里可以从response对象里面获取响应头和响应体
        // 获取响应头
        Map<String, Collection<String>> headers = response.headers();

        Tracer tracer = GlobalTracer.get();
        Span span = tracer.activeSpan();
        if (span != null) {
            Tags.HTTP_METHOD.set(span, "GET");
            span.finish();
        }
        return super.decode(response, type);
    }
}
