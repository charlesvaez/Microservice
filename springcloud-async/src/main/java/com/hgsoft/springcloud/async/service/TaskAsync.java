package com.hgsoft.springcloud.async.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Future;

@Component
public class TaskAsync {
    public Random random =new Random();

    @Async("taskExecutor")
    public Future<Long> doTaskOne() throws Exception {
        System.out.println("开始做任务一");
        long total = getTime( random);
        System.out.println("完成任务一，耗时：" + total+ "毫秒");

        return new  AsyncResult <>(total );
    }

    @Async("taskExecutor")
    public Future<Long> doTaskTwo() throws Exception {
        System.out.println("开始做任务二");
        long total = getTime( random);
        System.out.println("完成任务二，耗时：" + total + "毫秒");

        return new  AsyncResult <>(total);
    }

    @Async("taskExecutor")
    public Future<Long> doTaskThree() throws Exception {
        System.out.println("开始做任务三");
        long total = getTime( random);
        System.out.println("完成任务三，耗时：" + total + "毫秒");

        return new  AsyncResult <>(total);
    }

    private long getTime(Random random) throws Exception {
        long start = System.currentTimeMillis();

        Thread.sleep(random.nextInt(1000));

        long end = System.currentTimeMillis();

        return  end - start;
    }
}
