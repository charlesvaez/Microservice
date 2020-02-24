package com.hgsoft.springcloud.consumer.service;

import com.hgsoft.springcloud.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserServiceFallback implements UserService{
    @Override
    public List<User> getUserList() {
        User user =new User();
        user.setId(0);
        user.setName("default");
        user.setCreatetime(new Date());
        user.setPwd("123");
        List<User> users = new ArrayList<>(1);
        users.add(user);
        return users;
    }

    @Override
    public User getUserById(String userId) {
        User user =new User();
        user.setId(0);
        user.setName("default");
        user.setCreatetime(new Date());
        user.setPwd("123");
        return user;
    }
}
