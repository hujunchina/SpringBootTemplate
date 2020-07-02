package com.hujunchina.service;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/6/17 9:56 上午
 * @Version 1.0
 */
public class CallableTest {

    static class LogHandler implements Callable {

        @Override
        public Object call() throws Exception {
            System.out.println("callable Test");
            return "callable test";
        }
    }

    @Test
    public void test(){
        FutureTask futureTask = new FutureTask(new LogHandler());
        new Thread(futureTask).start();
    }
}
