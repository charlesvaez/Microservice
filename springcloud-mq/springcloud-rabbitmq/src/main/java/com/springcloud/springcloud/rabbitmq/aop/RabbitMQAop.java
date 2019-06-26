package com.springcloud.springcloud.rabbitmq.aop;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;


/**
 * @Author wyy
 * @Date 2018/11/16 12:00
 **/
@Aspect
@Order(999)
@Component
@Slf4j
public class RabbitMQAop {

    @Autowired
    private RetryTemplate retryTemplate;

    @Pointcut("@annotation(org.springframework.amqp.rabbit.annotation.RabbitListener)" +
            "||@annotation(org.springframework.amqp.rabbit.annotation.RabbitHandler)")
    private void pointcut() {
    }

    /**
     * 手动确认消息，如果消费失败，记录消息至数据库中
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object exec(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("=============pointcut================");
        final Object[] args = joinPoint.getArgs();
        Object obj = null;
        Message message = null;
        Channel channel = null;
        for (Object arg : args) {
            if (arg.getClass().isAssignableFrom(Message.class)) {
                message = (Message) arg;
            } else if (arg instanceof Channel) {
                channel = (Channel) arg;
            } else {
                obj = arg;
            }
        }
        Object result = null;//调用目标方法
        try {
            // obj为doWithRetry的返回结果，可以为任意类型
            Object finalObj = obj;
            Message finalMessage = message;
            result = retryTemplate.execute(
                    context -> joinPoint.proceed(),//重试执行的操作
                    context -> {//重试多次后依然失败
                        //mq消息消费出错，记录到数据库中
                        Throwable t = context.getLastThrowable();
                        try {
//                            String staceTrace = ExceptionUtils.getStackTrace(t);
                            String staceTrace = t.getMessage();
                            log.error("mq消费出错：{}",staceTrace);

//                            final MessageProperties properties = finalMessage.getMessageProperties();
//                            failRecordService.saveData(
//                                    properties.getReceivedExchange(),
//                                    properties.getReceivedRoutingKey(),
//                                    JSON.toJSONString(finalObj),
//                                    MqFailRecordType.BUSINESS_ERROR,
//                                    staceTrace
//                            );//记录失败的消息到数据库
                            log.info("记录失败的消息到数据库");
                        } catch (Exception e) {
                            log.error("记录mq失败消息出错: " + e.getMessage(), e);
                        }
                        return null;
                    });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("RabbitMQAop出错: " + e.getMessage(),e);
        }
        CachingConnectionFactory c =null;
        try {
            //手工ACK
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("mq应答出错：" + e.getMessage(),e);
        }
        return result;
    }
}
