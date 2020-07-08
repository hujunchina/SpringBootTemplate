package com.hujunchina.service.eventHandler;

import com.hujunchina.common.ConstTag;
import com.hujunchina.service.mqttDemo.ICarLocationSDK;
import com.sun.tools.internal.jxc.ap.Const;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

import java.beans.EventHandler;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/8 11:53 上午
 * @Version 1.0
 * Mqtt 消息接受处理中心，订阅者
 */
@Slf4j
public class MqttEventReceiver {

    private static final String MQTT_URL = "tcp://127.0.0.1:1883";

    private static final String DEVICE_TOPIC = "device/in/event";

    private static final String DEVICE_ID = "GateWayDoor001";

    private MqttClient mqttClient = null;

    public MqttEventReceiver() {
        try {
            mqttClient = new MqttClient(MQTT_URL, DEVICE_ID);
//            mqttClient.connect();
        } catch (Exception e) {
            log.info("Mqtt 事件接受者启动错误", e);
        }
    }

    /** 需要单独开线程运行*/
    // 为什么是这种设计方式？通过回调方法获取信息？？？
    public void start(){
        try {
            //【1】客户端设置 callback 回调类
            mqttClient.setCallback(new ReceiveEvent());

            //【2】客户端连接 mqtt broker
            mqttClient.connect();

            //【3】客户端监听topic消息
            mqttClient.subscribe(DEVICE_TOPIC);

            if (mqttClient.isConnected()) {
                log.info("MQTT center: 已经连接代理中心");
            }

        } catch (Exception e) {
            log.info("{} mqtt 订阅消息失败", ConstTag.EVENT);
        }
    }

    static class ReceiveEvent implements MqttCallback {

        @Override
        public void connectionLost(Throwable cause) {
            log.info("{} 连接丢失", ConstTag.EVENT);
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            // 根据消息类型调用不同的 EventHandler
            EventContext eventContext = new EventContext();
            log.info("messge : {}", message.toString());
            eventContext.setDp(Integer.valueOf(message.toString()));

            //【2】通过事件工厂得到相应的事件处理
            MqttEventHandler eventHandler = null;
            MqttEventFactory eventFactory = new MqttEventFactory();
            eventFactory.init();
            eventHandler = eventFactory.getEventHandler(eventContext.getDp());

            eventHandler.handler(eventContext);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            log.info("{} 接收完成", ConstTag.EVENT);
        }
    }

    public void disconnect(){
        try {
            mqttClient.disconnect();
        } catch (Exception e) {
            log.info("断开连接 Mqtt 失败");
        }
    }
}
