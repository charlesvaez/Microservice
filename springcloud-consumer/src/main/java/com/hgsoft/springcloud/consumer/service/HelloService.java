package com.hgsoft.springcloud.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name= "springcloud-provider")
public interface HelloService {
    @RequestMapping(value = "/hello")
    public String hello(@RequestParam(value = "name") String name);
}