package com.webapp.buildPC.testingSwagger;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest/hello")
public class testAPI {
    @GetMapping

    public String hello() {

        return "hello world";
    }
    @PostMapping("/post")
    public String hellopost(@RequestBody final  String hello){
        return hello;
    }
    @PutMapping("/put")
    public String helloput(@RequestBody final String hello){
        return hello;
    }
}
