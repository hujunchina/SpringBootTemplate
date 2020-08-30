package com.hujunchina.client.dubbo.async;

import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/5 4:15 下午
 * @Version 1.0
 * CompletableFuture 和 FutureTask 都实现了 Future接口，所以在线程方法上差不多。
 * CompletableFuture 实现了 CompletionStage接口，该接口提供控制异步的方法
 */
public class RsyncDemo {

    public void asyncCalculate(int a, int b){
        Instant start = Instant.now();
        CompletableFuture<Integer> future = new CompletableFuture<>();
        CompletableFuture<Integer> future1 = new CompletableFuture<>();
        new Thread(()->{
            int x = sleep(a, 1);
            future.complete(x);
        }).start();
        new Thread(()->{
            try {
                int y = sleep(b, 1);
                future1.complete(y);
            } catch (Exception e) {
                // 如果线程异常，直接退出，不会在主线程报错，
                // get只能阻塞，需要让future把异常传递到主线程
                future1.completeExceptionally(e);
            }
        }).start();

        try {
            Integer p = future.get();
            Integer q = future1.get();
            System.out.println(p + ".." + q);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Instant end = Instant.now();

        Duration duration = Duration.between(start, end);
        System.out.println(duration.toMillis());
    }

    public int sleep(int a, int timeout){
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return a;
    }

    // 提供了一个工厂方法，来传递参数。
    // 默认会交由 ForkJoinPoll 池中的某个执行线程运行，同时也提供了重载的方法，指定 Executor 。
    public void factory(){
        // supplyAsync方法的参数是 Supply，不用怕，直接使用lambda解决
        //supplier a function returning the value to be used
        //to complete the returned CompletableFuture
        // 一个技巧，对不知道的类，直接使用lambda避免实例化
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(()->sleep(1,1));
        // 设置线程池大小：
        // Num = cpu数量 * cpu期望利用率 * （ 1 + W/C）
        completableFuture.whenComplete((v,e)->{
            if (e != null) {
                e.printStackTrace();
            } else {
                System.out.println("Response: "+v);
            }
        });
        System.out.println("Executed before response return.");
    }

    public static void main(String[] args) {
        RsyncDemo demo = new RsyncDemo();
        demo.asyncCalculate(5,6);
        demo.factory();
        // 主线程会提前结束，导致子线程无法输出
        demo.sleep(1, 10);
    }
}
