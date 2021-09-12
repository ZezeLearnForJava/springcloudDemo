package com.zeze.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@FeignClient(value = "demo-rpc-api", configuration = {FeignRequestInterceptor.class})
public interface Sayhello {
    @GetMapping(value = "/sayHello")
    String sayHello();
}
