package com.hujunchina.service.eventHandler;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/8 2:09 下午
 * @Version 1.0
 */
public class Start {
    public static void main(String[] args) {
        MqttEventReceiver mqttEventReceiver = new MqttEventReceiver();
        mqttEventReceiver.start();
    }
}
