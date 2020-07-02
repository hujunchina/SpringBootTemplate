package com.hujunchina.service.cglibproxy;

import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 测试 CGLIB动态代理模式
 * 实现一个日志拦截类
 * @author hujunchina@outlook.com
 * @date 2020-06-16
 */
public class LogInterceptor implements MethodInterceptor {

    /**
     * @param o 表示要进行增强的对象
     * @param method 表示拦截的方法
     * @param args 数组表示参数列表，基本数据类型需要传入其包装类型，如int-->Integer、long-Long、double-->Double
     * @param methodProxy 表示对方法的代理，invokeSuper方法表示对被代理对象方法的调用
     * @return 执行结果
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        before();
        // 注意这里是调用 invokeSuper 而不是 invoke，否则死循环，
        // methodProxy.invokeSuper 执行的是原始类的方法，
        // method.invoke执行的是子类的方法
        Object result = methodProxy.invokeSuper(o, args);
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

    // 回调过滤器: 在CGLib回调时可以设置对不同方法执行不同的回调逻辑，或者根本不执行回调。
//    public class DaoFilter implements CallbackFilter {
//        @Override
//        public int accept(Method method) {
//            if ("select".equals(method.getName())) {
//                return 0;   // Callback 列表第1个拦截器
//            }
//            return 1;   // Callback 列表第2个拦截器，return 2 则为第3个，以此类推
//        }
//    }


}
