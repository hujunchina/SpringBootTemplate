package com.hujunchina.manager.domain;

/**
 * 测试 静态代理模式
 * @author hujunchina@outlook.com
 * @date 2020-06-16
 */
public interface ISubject {

    void proxyMethod();

    void jdkInvokeMethod(String arg);

    void cglibInvokeMethod(String arg1, String arg2);

}
