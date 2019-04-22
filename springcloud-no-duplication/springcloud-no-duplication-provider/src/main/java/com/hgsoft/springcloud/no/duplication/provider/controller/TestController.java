package com.hgsoft.springcloud.no.duplication.provider.controller;

import com.hgsoft.springcloud.no.duplication.provider.annotation.NoRepeatSubmit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    //http://localhost:9000/test
    @RequestMapping("/test")
    @NoRepeatSubmit
    public String test() {
        System.out.println("============producer test invoke==============");
        return ("程序逻辑返回");
    }

}