package com.hujunchina.common.mqtt.impl;

import com.hujunchina.common.mqtt.IMqttUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/6/20 11:43 上午
 * @Version 1.0
 */
public class MqttUtilsImpl implements IMqttUtils {

    private MqttClient mqttClient;

    @Override
    public boolean start() {
        String serverUrl = "127.0.0.1";

//        MqttClient mqttClient = new MqttClient();
        return false;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void disConnected() {

    }

    @Override
    public boolean push(Object msg) {
        return false;
    }
}
