package com.zeze.api;

import feign.Client;
import feign.Request;
import feign.Response;
import feign.opentracing.FeignSpanDecorator;
import io.opentracing.tag.Tags;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient;
import org.springframework.util.StreamUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DTSFeignClient extends FeignBlockingLoadBalancerClient {


    public DTSFeignClient(Client delegate, LoadBalancerClient loadBalancerClient, LoadBalancerProperties properties, LoadBalancerClientFactory loadBalancerClientFactory) {
        super(delegate, loadBalancerClient, properties, loadBalancerClientFactory);
    }

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {

        System.out.println(String.format("%s %s", request.httpMethod(), request.url()));

        try {
            Response response = super.execute(request, options);

            return response;
        } catch (Exception ex) {
            System.out.println("span 异常啦～～");
            throw ex;
        } finally {
            System.out.println("结束span");
        }
    }
}
