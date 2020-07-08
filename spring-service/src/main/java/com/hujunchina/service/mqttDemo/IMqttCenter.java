package com.hujunchina.service.mqttDemo;

import org.springframework.stereotype.Service;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/2 10:52 下午
 * @Version 1.0
 * mqtt 消息订阅中心
 */
@Service
public interface IMqttCenter {

    /** 订阅消息*/
    void subscribeMsg(String topic);
}
