package com.hgsoft.springcloud.consumer.service;

import com.hgsoft.springcloud.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.List;

@FeignClient(name= "springcloud-provider-mybatisplus",fallback = UserServiceFallback.class)
public interface UserService {

    @RequestMapping("/getUserList")
    public List<User> getUserList();

    @RequestMapping("/getUserById")
    public User getUserById(@RequestParam("userId") String userId);

}