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
}
