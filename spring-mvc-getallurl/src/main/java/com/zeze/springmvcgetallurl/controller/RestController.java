package com.zeze.springmvcgetallurl.controller;

import com.zeze.springmvcgetallurl.anno.Tracer;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{index}/test")
    @Tracer(OperationName = "路径参数")
    public String pathTest(@PathVariable("index") String index) {
        return "index";
    }

    @GetMapping("/testfile")
    public String pathttt() {
        return "test";
    }
}
