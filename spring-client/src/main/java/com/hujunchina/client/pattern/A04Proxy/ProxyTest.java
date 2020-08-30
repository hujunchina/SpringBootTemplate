package com.hujunchina.client.pattern.A04Proxy;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/6 10:37 上午
 * @Version 1.0
 * 静态代理模式（结构型）：代理的是类中的方法，最后还是调用原类的方法
 * dubbo中客户端存根服务：由于实现都放在服务端，客户端要想搞个缓存很麻烦
 * dubbo提供了存根服务，通过stub指定代理类，完成实现类操作
 */
public class ProxyTest {

    public static void main(String[] args) {
        // 服务端实现类由dubbo提供了
        DeviceFacadeStub stub = new DeviceFacadeStub(new DeviceFacadeImpl());

        System.out.println(stub.queryDeviceById("11"));
    }
}
