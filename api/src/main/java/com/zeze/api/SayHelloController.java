package com.zeze.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SayHelloController implements Sayhello {

    @GetMapping("/sayHello")
    @Override
    public String sayHello() {

        return "zeze";
    }
}
