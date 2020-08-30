package com.hujunchina.client.dubbo.async;

import java.util.concurrent.*;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/5 4:32 下午
 * @Version 1.0
 * Future，Callable，FutureTask
 * 有返回值的多线程Callable, executor.execute
 * 无返回值的多线程Runnable, executor.submit
 */
public class FutureTest {

    public void runnableTest(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                System.out.println(name + " 内部类写法");
            }
        }).start();

        new Thread(()->{
            String name = Thread.currentThread().getName();
            System.out.println(name + " Lambda 写法");
        }).start();
    }

    public void callableTest() throws ExecutionException, InterruptedException {
        FutureTask<String> res = new FutureTask<>(
                () -> {return "hujun";}
        );
        new Thread(res).start();
        System.out.println(res.get());
    }

    public void futureTest() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newCachedThreadPool();
        FutureTask<Integer> futureTask = (FutureTask<Integer>) executor.submit(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });
        // 设置拿到数据的超时时间
        // 如果sleep超过3秒，就拿不到数据
        System.out.println(futureTask.get(3, TimeUnit.SECONDS));
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTest futureTest = new FutureTest();
        futureTest.runnableTest();
        futureTest.callableTest();
        futureTest.futureTest();
    }
}
