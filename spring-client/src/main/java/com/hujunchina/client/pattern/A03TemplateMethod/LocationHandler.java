package com.hujunchina.client.pattern.A03TemplateMethod;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/6 9:57 上午
 * @Version 1.0
 * 位置处理类，在SDK中封装好了
 */
public class LocationHandler {

    // 云端通过http发送经纬度到SDK中
    // 在SDk中调用三方实现的方法，并返回给云端
    public String httpGet(Long latitude, Long longitude) {
        LocationService locationService = new LocationServiceImpl();

        return locationService.location(latitude, longitude);
    }
}
