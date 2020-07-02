package com.hujunchina.service;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.assertj.core.util.Lists;
import org.junit.runner.RunWith;

import java.sql.SQLOutput;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 测试
 *
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/6/16 7:42 下午
 * @Version 1.0
 */
@Slf4j
public class ForkJoinTest {

    public static String rpcCall(String ip, String param){
        System.out.println(ip+" rpcCall: "+param);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ip;
    }

    public static String rpcCall(String ip, String param, CountDownLatch countDownLatch) {
        System.out.println(ip+" rpcCall: "+param);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
        return ip;
    }

    private final static int PROCESSORS = Runtime.getRuntime().availableProcessors();

    private final static ThreadPoolExecutor executors =
            new ThreadPoolExecutor(10, 10*2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(5),
                    new BasicThreadFactory.Builder().namingPattern("executor-thread-%d").build(),
                    (r, executor) -> log.error(" executor pool is full!!"));

    public static void main(String[] args) throws InterruptedException {
        List<String> ipList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            ipList.add("192.168.1."+i);
        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        // 顺序执行
        ipList.forEach(ip -> rpcCall(ip, ip));
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
        stopwatch.reset();

        // 用 countDownLatch 执行
        stopwatch.start();
        CountDownLatch countDownLatch = new CountDownLatch(ipList.size());
        ipList.forEach(ip -> executors.execute(() -> rpcCall(ip, ip, countDownLatch)));
        countDownLatch.await();
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
        stopwatch.reset();

        // stream 带返回参数的
        stopwatch.start();
        List<CompletableFuture<String>> list = ipList.stream()
                .map(ip -> CompletableFuture.supplyAsync(() ->
                        rpcCall(ip, ip), executors)).collect(Collectors.toList());
        List<String> result = list.stream().map(CompletableFuture::join).collect(Collectors.toList());
        result.forEach(System.out::println);
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
        stopwatch.reset();

        // stream2 不带返回参数
        stopwatch.start();
        ipList.stream()
                .map(ip -> CompletableFuture.runAsync(() ->
                        rpcCall(ip, ip), executors)).collect(Collectors.toList());
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
        stopwatch.reset();

        // parallelStream 带参数返回
        stopwatch.start();
        result = ipList.parallelStream()
                .map(ip -> rpcCall(ip, ip)).collect(Collectors.toList());
        result.forEach(System.out::println);
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}
