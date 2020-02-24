package com.hgsoft.springcloud.provider.mybatis.plus.controller;

import com.hgsoft.springcloud.entity.User;
import com.hgsoft.springcloud.provider.mybatis.plus.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    //http://localhost:8888/getUserList
    @RequestMapping("/getUserList")
    public List<User> getUserList(){
        return userService.getUserList();
    }

    //http://localhost:8888/getUserListByName?userName=xiaoli
    //条件查询
    @GetMapping("/getUserListByName")
    public List<User> getUserListByName(String userName)
    {
        return userService.getUserListByName(userName);
    }

    //http://localhost:9999/saveUser?userName=xiaoli&userPassword=111
    //保存用户
    @RequestMapping("/saveUser")
    public String saveUser(String userName,String userPassword)
    {
        User user = new User(userName,userPassword,new Date());
        return userService.saveUser(user);

    }

    //http://localhost:8888/updateUser?id=5&userName=xiaoli&userPassword=111
    //修改用户
    @RequestMapping("/updateUser")
    public String updateUser(Integer id,String userName,String userPassword)
    {
        User user = new User(id,userName,userPassword);
       return userService.updateUser(user);
    }


    //http://localhost:9000/getUserById?userId=1
    //根据Id查询User
    @RequestMapping("/getUserById")
    public User getUserById(String userId)
    {
        log.debug("userId:{}",userId);
        return userService.selectById(userId);
    }

    //http://localhost:8888/getUserListByPage?pageNumber=1&pageSize=2
    //条件分页查询
    @RequestMapping("/getUserListByPage")
    public List<User> getUserListByPage(Integer pageNumber,Integer pageSize)
    {
        return userService.getUserListByPage(pageNumber,pageSize);
    }

}
