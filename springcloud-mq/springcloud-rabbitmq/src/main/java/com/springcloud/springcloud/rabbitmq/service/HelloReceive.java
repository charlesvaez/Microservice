package com.springcloud.springcloud.rabbitmq.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HelloReceive {

    //监听器监听指定的Queue
    @RabbitListener(queuesToDeclare = @Queue(name = "queue", durable = "true"))
    public void process(String str, Message message, Channel channel) {
        process(str);

    }

    public void process(String str){
        System.out.println("Receive:"+str);
    }
}
