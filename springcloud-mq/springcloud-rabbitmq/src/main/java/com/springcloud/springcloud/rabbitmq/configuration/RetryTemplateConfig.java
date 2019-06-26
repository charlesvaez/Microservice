package com.springcloud.springcloud.rabbitmq.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * @Author wyy
 * @Date 2018/9/7 19:56
 **/
@Configuration
public class RetryTemplateConfig {

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate oRetryTemplate = new RetryTemplate();
        SimpleRetryPolicy oRetryPolicy = new SimpleRetryPolicy();
        oRetryPolicy.setMaxAttempts(3);// 重试3次
        FixedBackOffPolicy offPolicy = new FixedBackOffPolicy();
        offPolicy.setBackOffPeriod(2000);//重试间隔2s
        oRetryTemplate.setRetryPolicy(oRetryPolicy);
        oRetryTemplate.setBackOffPolicy(offPolicy);
        return oRetryTemplate;
    }
}
