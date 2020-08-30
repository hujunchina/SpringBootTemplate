package com.hujunchina.client.pattern.A04Proxy;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/6 10:52 上午
 * @Version 1.0
 * 客服端要实现和代理类同样的接口
 */
public class DeviceFacadeStub implements DeviceFacade {

    private final DeviceFacade deviceFacade;

    public DeviceFacadeStub(DeviceFacade deviceFacade) {
        this.deviceFacade = deviceFacade;
    }

    @Override
    public String queryDeviceById(String id) {
        // 此代码在客户端执行, 你可以在客户端做ThreadLocal本地缓存，或预先验证参数是否合法，等等
        try {
            before();
            // 最后还是调用的服务器类方法
            return deviceFacade.queryDeviceById(id);
        } catch (Exception e) {
            // 你可以容错，可以做任何AOP拦截事项
            return "容错数据";
        }
    }

    public void before() {
        System.out.println("查询设备执行前");
    }
}
