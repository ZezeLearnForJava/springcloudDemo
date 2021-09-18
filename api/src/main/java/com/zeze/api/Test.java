package com.zeze.api;

import feign.Client;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.openfeign.loadbalancer.OnRetryNotEnabledCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Test {

    @Bean
    @Conditional(OnRetryNotEnabledCondition.class)
    public Client feignClient(LoadBalancerClient loadBalancerClient, LoadBalancerProperties properties,
                              LoadBalancerClientFactory loadBalancerClientFactory) {

        return new DTSFeignClient(new Client.Default(null, null), loadBalancerClient, properties,
                loadBalancerClientFactory);
    }

}
