package com.hgsoft.springcloud.no.duplication.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name= "springcloud-no-duplication-provider")
public interface HelloService {
    @RequestMapping(value = "/test")
    public String test();
}