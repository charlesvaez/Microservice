package com.hgsoft.springcloud.consumer.controller;

import com.hgsoft.springcloud.consumer.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//http://localhost:9001/hello/hcx
//经过网关 http://localhost:8888/consumer/hello/hcx
@RestController
@Slf4j
public class ConsumerController {

    @Autowired
    HelloService helloService;

    @RequestMapping("/hello/{name}")
    public String index(@PathVariable("name") String name) {
        long startime = System.currentTimeMillis();

        String result = helloService.hello(name);

        long endtime = System.currentTimeMillis();
        log.info("invoke time: {}",(endtime-startime));
        System.out.println("invoke time: "+(endtime-startime));

        return result;
    }

}