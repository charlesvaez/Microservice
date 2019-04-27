package com.hgsoft.springcloud.async.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Component
public class TaskAsyncCompletable {
    public Random random =new Random();

    @Async("taskExecutor")
    public CompletableFuture<Long> doTaskOne() throws Exception {
        System.out.println("开始做任务一");
        long total = getTime( random);
        System.out.println("完成任务一，耗时：" + total+ "毫秒");

        return CompletableFuture.completedFuture(total);
    }

    @Async("taskExecutor")
    public CompletableFuture<Long> doTaskTwo() throws Exception {
        System.out.println("开始做任务二");
        long total = getTime( random);
        System.out.println("完成任务二，耗时：" + total + "毫秒");

        return CompletableFuture.completedFuture(total);
    }

    @Async("taskExecutor")
    public CompletableFuture<Long> doTaskThree() throws Exception {
        System.out.println("开始做任务三");
        long total = getTime( random);
        System.out.println("完成任务三，耗时：" + total + "毫秒");

        return CompletableFuture.completedFuture(total);
    }

    private long getTime(Random random) throws Exception {
        long start = System.currentTimeMillis();

        Thread.sleep(random.nextInt(1000));

        long end = System.currentTimeMillis();

        return  end - start;
    }
}
