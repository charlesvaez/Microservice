package com.hgsoft.springcloud.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class UriKeyResolver  implements KeyResolver {
    private final Logger logger = LoggerFactory.getLogger(UriKeyResolver.class);

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();

        String path =  serverHttpRequest.getURI().getPath();
        logger.info("path > {}",path);
        return Mono.just(path);
    }

}
