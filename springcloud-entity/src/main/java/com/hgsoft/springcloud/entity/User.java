package com.hgsoft.springcloud.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_user")
public class User implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private String name;
    private String pwd;
    private Date createtime;

    public User() {
    }

    public User(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public User(int id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public User(String name, String pwd, Date createtime) {
        this.name = name;
        this.pwd = pwd;
        this.createtime = createtime;
    }


}
