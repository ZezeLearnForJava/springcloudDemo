package com.zeze.springmvcgetallurl.controller;

import com.zeze.springgetallurl.anno.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {



    @GetMapping("/test01")
    @Tracer(OperationName = "获取角色")
    public String getAllUrl() {
        return "123";
    }

    @PostMapping("/test01")
    @Tracer(OperationName = "增加角色")
    public String getAllUrlTest() {
        return "123";
    }
}
