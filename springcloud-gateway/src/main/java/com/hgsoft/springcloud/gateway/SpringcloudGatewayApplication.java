package com.hgsoft.springcloud.gateway;

import com.hgsoft.springcloud.gateway.filter.TokenFilter;
import com.hgsoft.springcloud.gateway.filter.UriKeyResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.client.WebSocketClient;

@SpringBootApplication
//@Configuration
//@EnableAutoConfiguration
public class SpringcloudGatewayApplication {

    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }

//    限流
//    @Bean
//    public UriKeyResolver uriKeyResolver() {
//        return new UriKeyResolver();
//    }

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudGatewayApplication.class, args);
    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("path_route", r -> r.path("/about")
//                        .uri("http://ityouknow.com"))
//                .build();
//    }
}
