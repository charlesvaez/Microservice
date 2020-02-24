package com.hgsoft.springcloud.consumer.controller;

import com.alibaba.fastjson.JSON;
import com.hgsoft.springcloud.consumer.service.HelloService;
import com.hgsoft.springcloud.consumer.service.UserService;
import com.hgsoft.springcloud.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//http://localhost:9001/hello/hcx
//经过网关 http://localhost:8888/consumer/hello/hcx
@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/getUserList")
    public String getUserList() {
        List<User> users =  userService.getUserList();
        if(null !=users && users.size()>0){
            return  JSON.toJSONString(users);
        }

        return "[]";
    }

    @RequestMapping(value="/getUserById",method= {RequestMethod.GET,RequestMethod.POST})
    public String getUserById(String userId)
    {
        User user = userService.getUserById(userId);
        if(null !=  user){
            return JSON.toJSONString(user);
        }
        return "{}";
    }


}