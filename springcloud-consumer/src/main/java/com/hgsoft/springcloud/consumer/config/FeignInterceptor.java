package com.hgsoft.springcloud.consumer.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class FeignInterceptor implements RequestInterceptor {

    private static final String HTTP_HEADER_VERSION = "version";
    @Autowired
    private HttpServletRequest request;

    @Override
    public void apply(RequestTemplate requestTemplate) {
//        ServletRequestAttributes servletRequestAttributes =
//                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = servletRequestAttributes.getRequest();

        String version = request.getHeader(HTTP_HEADER_VERSION);

        System.out.println("================version=================="+version);
        requestTemplate.header(HTTP_HEADER_VERSION, version);

    }
}

