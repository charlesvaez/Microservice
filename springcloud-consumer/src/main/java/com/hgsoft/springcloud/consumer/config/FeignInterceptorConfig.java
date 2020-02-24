package com.hgsoft.springcloud.consumer.config;

import com.netflix.hystrix.strategy.HystrixPlugins;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

import javax.annotation.PostConstruct;
import javax.servlet.annotation.WebListener;


@Configuration
public class FeignInterceptorConfig{
    @Bean
    public RequestInterceptor requestInterceptor(){
        return new FeignInterceptor();
    }

    @PostConstruct
    public void init() {
        HystrixPlugins.getInstance().registerConcurrencyStrategy(new RequestContextHystrixConcurrencyStrategy());
    }

}
