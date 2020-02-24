package com.hgsoft.springcloud.provider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

// trace com.hgsoft.springcloud.provider.controller.HelloController index
@RestController
public class HelloController {
    //http://localhost:9000/hello?name=hcx
    //经过网关：http://localhost:8888/provider/hello?name=hcx
    @RequestMapping("/hello")
    public String index(@RequestParam String name) {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String version = request.getHeader("version");
        System.out.println("--------------------------------"+version);
        return "hello "+name+"，this is first messge,version: "+version;
    }
}
