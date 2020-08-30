package com.hujunchina.client.pattern.A03TemplateMethod;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/5 2:22 下午
 * @Version 1.0
 * 模版方法（行为型）：把业务逻辑或算法的实现推迟到子类
 * 提供一个算法架构，由子类具体实现
 * 设计一个SDK二次开发的流程调用。
 * 地图二次开发，定位功能修复功能
 */
public class TemplateMethodTest {

    public static void main(String[] args) {

        Long latitude = 101L;
        Long longitude = 102L;

        // 定位中
        System.out.printf("云端给的定位服务, latitude=%d, longitude=%d%n", latitude, longitude);

        LocationTemplate locationTemplate = new LocationTemplate();
        locationTemplate.repairOffset(latitude, longitude);
    }
}
