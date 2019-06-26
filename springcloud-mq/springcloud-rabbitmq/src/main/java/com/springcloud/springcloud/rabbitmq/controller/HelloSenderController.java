package com.springcloud.springcloud.rabbitmq.controller;

import com.springcloud.springcloud.rabbitmq.service.HelloSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloSenderController {
    @Autowired
    private HelloSender helloSender;

    @RequestMapping("/send")
    public String send(){
        for(int i = 0;i < 2;i++){
            new Thread(){
                @Override
                public void run(){
                    helloSender.send();
                }
            }.start();
        }

        return "send success";
    }
}
