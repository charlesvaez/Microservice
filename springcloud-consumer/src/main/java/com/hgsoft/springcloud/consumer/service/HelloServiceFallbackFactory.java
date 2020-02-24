package com.hgsoft.springcloud.consumer.service;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloServiceFallbackFactory implements FallbackFactory<HelloServiceFallback> {
    @Override
    public HelloServiceFallback create(Throwable throwable) {

        log.error("降级原因：",throwable);

        return new HelloServiceFallback();
    }
}
