package com.hgsoft.springcloud.provider2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringcloudProvider2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudProvider2Application.class, args);
    }

}
