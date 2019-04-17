package com.hgsoft.springcloud.gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {
    private final Logger logger = LoggerFactory.getLogger(FallbackController.class);

    @RequestMapping("/fallbackcontroller")
    public Mono<String> fallback(){
        logger.info("==================fallback======================");
        return Mono.just("fallback");
    }
}
