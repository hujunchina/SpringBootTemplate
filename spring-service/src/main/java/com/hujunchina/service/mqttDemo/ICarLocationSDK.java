package com.hujunchina.service.mqttDemo;

import org.springframework.stereotype.Service;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/2 10:16 下午
 * @Version 1.0
 * 汽车定位信息自动上传SDK
 */
@Service
public interface ICarLocationSDK {

    /** 上传（经度，纬度）*/
    void pushLocation(String longitude, String latitude);

    /** 断开连接*/
    void disconnect();

    /** 心跳包*/
    void heartBeat();
}
