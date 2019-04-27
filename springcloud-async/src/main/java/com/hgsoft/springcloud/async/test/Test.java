package com.hgsoft.springcloud.async.test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        //用例一
//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> cale(50));
//        try {
//            System.out.println(future.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

        //用例二
//        CompletableFuture<Void> future = CompletableFuture
//                .supplyAsync(() -> cale(50))
//                .thenApply(i -> Integer.toString(i))
//                .thenApply(str -> "\"" + str + "\"")
//                .thenAccept(System.out::println);
//        try {
//            System.out.println(future.get());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //用例三 CompletableFuture中的异常处理
//        CompletableFuture<Void> future = CompletableFuture
//                .supplyAsync(() -> cale(50))
//                .exceptionally(ex -> {
//                    System.out.println("ex.toString() = " + ex.toString());
//                    return 0;
//                })
//                .thenApply(i -> Integer.toString(i))
//                .thenApply(str -> "\"" + str + "\"")
//                .thenAccept(System.out::println);
//        try {
//            System.out.println(future.get());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //用例四，组合多个CompletableFuture
        CompletableFuture<Void> future = CompletableFuture
                .supplyAsync(() -> cale(50))
                .thenCompose(i -> CompletableFuture
                        .supplyAsync(() -> cale(i)))
                .thenApply(i -> Integer.toString(i))
                .thenApply(str -> "\"" + str + "\"")
                .thenAccept(System.out::println);
        try {
            System.out.println(future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Integer cale(Integer para) {
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return para * para;
    }
}
