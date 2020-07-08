package com.hujunchina.service.mqttDemo.Impl;

import com.google.common.eventbus.Subscribe;
import com.hujunchina.service.mqttDemo.IMqttCenter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/3 9:24 上午
 * @Version 1.0
 */
@Slf4j
@Service
public class MqttCenter implements IMqttCenter {

    private final String BROKER_URL = "tcp://127.0.0.1:1883";
    private final String CLIENT_ID = "CAR/LOCATION/HJ239712";
    private final String CAR_LOCATION_TOPIC = "car/location/ef239712";
    private MqttClient mqttClient;

    public MqttCenter(){
        try {
            mqttClient = new MqttClient(BROKER_URL, CLIENT_ID);
//            mqttClient.connect();
        } catch (Exception e) {
            log.info("MQTT Center location: ", e);
        }
    }

    @Override
    public void subscribeMsg(String topic) {
        try {
            //【1】定义回调类
            mqttClient.setCallback(new SubscribeCallBack());

            //【2】连接mqtt
            mqttClient.connect();

            //【3】订阅主题
            mqttClient.subscribe(CAR_LOCATION_TOPIC);

            if (mqttClient.isConnected()) {
                log.info("MQTT center: 已经连接代理中心");
            }
        } catch (Exception e) {
            log.info("MQTT center location: ", e);
        }
    }

    @Slf4j
    static class SubscribeCallBack implements MqttCallback {

        @Override
        public void connectionLost(Throwable cause) {

        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            log.info("Message arrived. Topic: " + topic + " Message: " + message.toString());

            if ("car/LWT".equals(topic))
            {
                log.error("Sensor gone!");
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }
    }
}
