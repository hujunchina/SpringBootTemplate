package com.hujunchina.client.base;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/6 11:00 下午
 * @Version 1.0
 */
public class Proxy {
    public interface Working{
        void workingOne();
        void workingTwo();
    }
    public static class WorkingImpl implements Working {
        @Override
        public void workingOne() {
            System.out.println("working one");
        }

        @Override
        public void workingTwo() {
            System.out.println("working two");
        }

        public static void main(String[] args) {
//        JDK动态代理
            Working working = new WorkingImpl();
            Working proxy = Proxy.getProxy(working);
            proxy.workingOne();
            proxy.workingTwo();
//        CGLIB动态代理
            Working working1 = new WorkingImpl();
            Working proxyCglib = Proxy.getProxyCGLIB();
            proxyCglib.workingOne();
            proxyCglib.workingTwo();

        }
    }
    public static Working getProxy(final Working woring){
        Working proxy = (Working) java.lang.reflect.Proxy.newProxyInstance(woring.getClass().getClassLoader(),
                woring.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        if("workingTwo".equals(method.getName())){
                            System.out.println("rest 10 minutes");
                        }
                        return method.invoke(woring, objects);
                    }
                });
        return proxy;
    }

    public static Working getProxyCGLIB(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Working.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                if("workingTwo".equals(method.getName())){
                    System.out.println("rest 10 minutes from cglib");
                }
                return methodProxy.invokeSuper(o, objects);
            }
        });
        Working proxy = (Working) enhancer.create();
        return proxy;
    }
}
