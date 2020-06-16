package com.hujunchina.service;

/*
 * 测试 静态代理模式
 * @author hujunchina@outlook.com
 * @date 2020-06-16
 */

import com.hujunchina.manager.domain.ISubject;
import com.hujunchina.manager.domain.impl.RealObjectImpl;
import com.hujunchina.service.staticproxy.StaticProxy;

public class StaticProxyTest {

    public static void main(String[] args) {
        // 【1】 接口声明，实现泛化
        ISubject subject = new RealObjectImpl();


        // 【2】 调用接口的实现方法
        StaticProxy staticProxy = new StaticProxy(subject);

        // 【3】 替换为代理类
        staticProxy.proxyMethod();
    }

}
