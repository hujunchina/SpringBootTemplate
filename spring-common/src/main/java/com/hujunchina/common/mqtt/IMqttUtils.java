package com.hujunchina.common.mqtt;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/6/20 11:40 上午
 * @Version 1.0
 */
public interface IMqttUtils {

    /** 启动*/
    boolean start();

    /** 连接状态*/
    boolean isConnected();

    /** 关闭连接*/
    void disConnected();

    /** 发送消息*/
    boolean push(Object msg);

}
