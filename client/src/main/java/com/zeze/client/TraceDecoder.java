package com.zeze.client;

import feign.FeignException;
import feign.Response;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * 解码器
 */
class TraceDecoder extends SpringDecoder {

    TraceDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        super(messageConverters);
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {

        System.out.println("api decode:");

        // 这里可以从response对象里面获取响应头和响应体

        // 获取响应头
        Map<String, Collection<String>> headers = response.headers();

        return super.decode(response, type);
    }
}
