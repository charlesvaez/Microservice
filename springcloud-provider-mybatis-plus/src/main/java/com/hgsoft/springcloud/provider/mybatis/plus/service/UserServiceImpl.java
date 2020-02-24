package com.hgsoft.springcloud.provider.mybatis.plus.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hgsoft.springcloud.entity.User;
import com.hgsoft.springcloud.provider.mybatis.plus.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService{
    @Autowired
    private UserMapper userMapper;

    @GetMapping("getUserList")
    public List<User> getUserList(){
        return userMapper.getUserList();
    }

    //条件查询
    public List<User> getUserListByName(String userName)
    {
        Map map = new HashMap();
        map.put("user_name", userName);
        return userMapper.selectByMap(map);
    }

    //保存用户
    @Transactional
    public String saveUser( User user)
    {
        Integer index = userMapper.insert(user);
        if(index>0){
            return "新增用户成功。";
        }else{
            return "新增用户失败。";
        }
    }

    //修改用户
    @Transactional
    public String updateUser( User user)
    {
        Integer index = userMapper.updateById(user);
        if(index>0){
            return "修改用户成功，影响行数"+index+"行。";
        }else{
            return "修改用户失败，影响行数"+index+"行。";
        }
    }

    //条件分页查询
    public List<User> getUserListByPage(Integer pageNumber, Integer pageSize)
    {
        Page<User> page =new Page<>(pageNumber,pageSize);
        EntityWrapper<User> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name", "xiaoli");

        return userMapper.selectPage(page,entityWrapper);
    }
}
