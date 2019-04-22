package com.hgsoft.springcloud.no.duplication.consumer.controller;

import com.hgsoft.springcloud.no.duplication.consumer.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//http://localhost:9011/hello/hcx
@RestController
public class ConsumerController {

    @Autowired
    HelloService helloService;

    @RequestMapping("/test")
    public String index() {
        System.out.println("============consumer test invoke==============");
        return helloService.test();

    }

}