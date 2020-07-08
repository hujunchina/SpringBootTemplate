package com.hujunchina.service;

import com.hujunchina.service.mqttDemo.ICarLocationSDK;
import com.hujunchina.service.mqttDemo.IMqttCenter;
import com.hujunchina.service.mqttDemo.Impl.CarLocationSDK;
import com.hujunchina.service.mqttDemo.Impl.MqttCenter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/3 9:38 上午
 * @Version 1.0
 */

@Slf4j
public class MqttTest {

//    @Resource
//    IMqttCenter mqttCenter;
//
//    @Resource
//    ICarLocationSDK carLocationSDK;

    @Test
    public void mqtt() throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("MQTT test 启动订阅中心");
                MqttCenter mqttCenter = new MqttCenter();
                mqttCenter.subscribeMsg("topic");
            }
        }).start();

        Thread.sleep(3000);

        log.info("MQTT test 启动设备端");
        CarLocationSDK carLocationSDK = new CarLocationSDK();
        carLocationSDK.start();
    }

}
