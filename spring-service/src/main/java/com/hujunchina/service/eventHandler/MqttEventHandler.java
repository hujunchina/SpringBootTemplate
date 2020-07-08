package com.hujunchina.service.eventHandler;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/7 10:13 下午
 * @Version 1.0
 * 封装同一事件处理类，通过工厂类实现
 */
public interface MqttEventHandler {

    /** 事件处理方法*/
    void handler(EventContext eventContext);
}
