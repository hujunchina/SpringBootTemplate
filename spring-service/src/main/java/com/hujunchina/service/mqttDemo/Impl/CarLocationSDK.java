package com.hujunchina.service.mqttDemo.Impl;

import com.hujunchina.service.mqttDemo.ICarLocationSDK;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/2 10:20 下午
 * @Version 1.0
 */
@Slf4j
@Service
public class CarLocationSDK implements ICarLocationSDK {

    private final String BROKER_URL = "tcp://127.0.0.1:1883";
    private final String CLIENT_ID = "CAR/LOCATION/239712";
    private final String CAR_LOCATION_TOPIC = "car/location/ef239712";
    private MqttClient mqttClient;

    public CarLocationSDK(){
        try {
            //【1】实例化一个mqtt客户端
            mqttClient = new MqttClient(BROKER_URL, CLIENT_ID);

        } catch (Exception e) {
            log.info("MQTT Car location init: ", e);
        }
    }

    public void start() {
        try {
            //【2】实例化一个mqtt连接参数
            MqttConnectOptions options = new MqttConnectOptions();
            //【3】设置清除session false
            options.setCleanSession(false);
            //【4】设置意外时发送的消息，QoS=2表示只送达一次，并且消息一直保存
            options.setWill(mqttClient.getTopic("car/LWT"), "服务终止".getBytes(), 0, false);
            //【5】建立连接
            mqttClient.connect(options);

            Random random = new Random();
            while (true) {
                this.pushLocation(String.valueOf(random.nextFloat()), String.valueOf(random.nextFloat()));
                Thread.sleep(3000);
            }

        } catch (Exception e) {
            log.info("MQTT Car location: ", e);
        }
    }

    @Override
    public void pushLocation(String longitude, String latitude) {
        try {
            //【1】构建mqtt消息
            String msg = longitude+":"+latitude;
            MqttMessage mqttMessage = new MqttMessage(msg.getBytes());
            //【2】构建mqtt主题
            final MqttTopic mqttTopic = mqttClient.getTopic(CAR_LOCATION_TOPIC);
            //【3】通过主题发布消息
            mqttTopic.publish(mqttMessage);
            log.info("MQTT Car location: Send Message "+msg);
        } catch (MqttException e) {
            log.info("MQTT Car location: ", e);
        }
    }

    @Override
    public void disconnect() {
        log.info("MQTT Car location: disconnect");
    }

    @Override
    public void heartBeat() {
        log.info("MQTT Car location: heart beat");
    }
}
