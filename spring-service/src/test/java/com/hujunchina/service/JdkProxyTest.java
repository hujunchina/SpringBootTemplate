package com.hujunchina.service;

import com.hujunchina.manager.domain.ISubject;
import com.hujunchina.manager.domain.impl.RealObjectImpl;
import com.hujunchina.service.jdkproxy.LogHandler;
import com.sun.deploy.net.proxy.ProxyUtils;
import com.sun.tools.javac.code.Attribute;

import java.lang.reflect.Proxy;

/**
 * 测试 JDK动态代理模式
 * 实现一个日志增强类
 * @author hujunchina@outlook.com
 * @date 2020-06-16
 */
public class JdkProxyTest {

    /** 需要使用Proxy静态类*/
    public static void main(String[] args) throws Throwable {
        //【1】 实现代理对象
        ISubject subject = new RealObjectImpl();

        //【2】获取 ClassLoad 对象
        ClassLoader classLoader = subject.getClass().getClassLoader();

        //【3】获取所有接口的 Class，这里的 RealObjectImpl 只实现了一个接口UserService，
        // 要代理的类是实现了接口，就要去找接口，如果实现的是类，就找类
        Class[] interfaces = subject.getClass().getInterfaces();

        //【4】实例化 LogHandler 对象,创建一个将传给代理类的调用请求处理器，处理所有的代理对象上的方法调用
        LogHandler logHandler = new LogHandler(subject);

        //【5】调用 Proxy 生成代理类
        //a.JDK会通过根据传入的参数信息动态地在内存中创建和.class 文件等同的字节码(interfaces)
        //b.然后根据相应的字节码转换成对应的class，
        //c.然后调用newInstance()创建代理实例
        ISubject subject1 = (ISubject) Proxy.newProxyInstance(classLoader, interfaces, logHandler);

        subject1.jdkInvokeMethod("test");

        // 保存JDK动态代理生成的代理类，类名保存为 UserServiceProxy
        //ProxyUtils.generateClassFile(subject.getClass(), "SubjectProxy");
    }
}
