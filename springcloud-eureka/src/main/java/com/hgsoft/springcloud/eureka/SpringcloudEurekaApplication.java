package com.hgsoft.springcloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * java -jar spring-cloud-eureka-0.0.1-SNAPSHOT.jar --spring.profiles.active=node1
 * java -jar spring-cloud-eureka-0.0.1-SNAPSHOT.jar --spring.profiles.active=node2
 */
@SpringBootApplication
@EnableEurekaServer
public class SpringcloudEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudEurekaApplication.class, args);
    }

}
