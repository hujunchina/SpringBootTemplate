package com.hujunchina.manager.domain.impl;

import com.hujunchina.manager.domain.ISubject;

/**
 * 测试 静态代理模式
 * @author hujunchina@outlook.com
 * @date 2020-06-16
 */
public class RealObjectImpl implements ISubject {

    @Override
    public void proxyMethod() {
        System.out.println("静态代理方法执行了");
    }

    @Override
    public void jdkInvokeMethod(String arg) {
        System.out.println("JDK 代理执行了，参数："+arg);
    }

    @Override
    public void cglibInvokeMethod(String arg1, String arg2) {
        System.out.println("CGLIB 代理执行了，参数："+arg1+" "+arg2);
    }
}
