package com.hujunchina.client.pattern.A04Proxy;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/6 10:51 上午
 * @Version 1.0
 * 服务端，查询设备接口实现类
 */
public class DeviceFacadeImpl implements DeviceFacade{
    @Override
    public String queryDeviceById(String id) {
        return "设备信息"+id;
    }
}
