package com.hujunchina.service.staticproxy;

import com.hujunchina.manager.domain.ISubject;
import com.hujunchina.manager.domain.impl.RealObjectImpl;

/*
 * 测试 静态代理模式
 * @author hujunchina@outlook.com
 * @date 2020-06-16
 */
public class StaticProxy implements ISubject {

    /** 代理类对真实类依赖，并调用其方法*/
    private ISubject subject;

    public StaticProxy(ISubject subject){
        this.subject = subject;
    }

    public void before(){
        System.out.println("静态代理执行前");
    }

    public void after(){
        System.out.println("静态代理执行后");
    }

    @Override
    public void proxyMethod() {
        this.before();
        subject.proxyMethod();
        this.after();
    }
}
