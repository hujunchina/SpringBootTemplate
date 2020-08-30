package com.hujunchina.client.pattern.A04Proxy;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/6 10:50 上午
 * @Version 1.0
 * 服务端，查询设备门面，对外暴露
 */
public interface DeviceFacade {

    String queryDeviceById(String id);
}
