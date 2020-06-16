package com.hujunchina.service;

import com.hujunchina.manager.domain.impl.RealObjectImpl;
import com.hujunchina.service.cglibproxy.LogInterceptor;
import net.sf.cglib.proxy.Enhancer;

/**
 * 测试 CGLIB动态代理模式
 * 实现一个日志拦截类
 * @author hujunchina@outlook.com
 * @date 2020-06-16
 */
public class CglibTest {

    public static void main(String[] args) {

        //【1】实例化日志拦截器
        LogInterceptor logInterceptor = new LogInterceptor();

        //【2】实例化增强类
        Enhancer enhancer = new Enhancer();

        //【3】设定父类
        enhancer.setSuperclass(RealObjectImpl.class);

        //【4】设定拦截器
        enhancer.setCallback(logInterceptor);

        //【5】实例化自己的代理类，重点不是new实例化，而是通过增强类来生成
        RealObjectImpl realObject = (RealObjectImpl) enhancer.create();

        realObject.cglibInvokeMethod("arg1", "arg2");
    }
}
