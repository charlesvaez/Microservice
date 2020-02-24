package com.hgsoft.springcloud.provider.mybatis.plus.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;


@RestController
public class DataSourceController {
    
    @RequestMapping("/dataSource")
    public String test(HttpServletRequest request){
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());

        DruidDataSource dataSource = context.getBean("dataSource", DruidDataSource.class);

        for(int i = 1 ; i < 200;i++){
            new Thread(){
                @Override
                public void run() {
                    DruidPooledConnection connection = null;
                    try {
                        connection = dataSource.getConnection();

                        System.out.println("thread: "+Thread.currentThread().getId()+" ,connection: "+connection);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }finally{
                        try {
                            TimeUnit.MINUTES.sleep(1L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(connection != null){
                            try {
                                connection.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }.start();

        }

        try {
            TimeUnit.MINUTES.sleep(6L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(dataSource != null){
            return "{\"status\":\"ok\"}";
        }
        else
        {
            return "{\"status\":\"fail\"}";
        }

    }
    
}
