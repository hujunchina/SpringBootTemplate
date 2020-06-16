package com.hujunchina.service.jdkproxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 测试 JDK动态代理模式
 * 实现一个日志增强类
 * @author hujunchina@outlook.com
 * @date 2020-06-16
 */
public class LogHandler implements InvocationHandler {

    /** 代理类对象，具体的操作方法*/
    Object target;

    public LogHandler(Object object){
        this.target = object;
    }

    /** 三个参数：代理类，方法，参数*/
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        /** 调用 target 的 method 方法 */
        Object result = method.invoke(target, args);
        after();
        return result;
    }

    // 调用invoke方法之前执行
    private void before() {
        System.out.println(String.format("log start time [%s] ", new Date()));
    }
    // 调用invoke方法之后执行
    private void after() {
        System.out.println(String.format("log end time [%s] ", new Date()));
    }
}
