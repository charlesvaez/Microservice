package com.hgsoft.springcloud.consumer.service;

import org.springframework.stereotype.Component;

@Component
public class HelloServiceFallback implements HelloService{
    @Override
    public String hello(String name) {
        return "===================================fallback======================================";
    }
}
