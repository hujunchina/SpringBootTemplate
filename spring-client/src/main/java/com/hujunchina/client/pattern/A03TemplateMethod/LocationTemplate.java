package com.hujunchina.client.pattern.A03TemplateMethod;

import javax.xml.stream.Location;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/6 9:42 上午
 * @Version 1.0
 * 云端定位服务处理类
 * 云端有自己的算法架构
 * 一个数据库存储事务
 */
public class LocationTemplate {

    public void repairOffset(Long latitude, Long longitude) {
        // 不能实例化三方写的实现类，因为云端并不知道三方的类名
        LocationService locationService;

        // 通过handler类简化模拟http请求
        LocationHandler handler = new LocationHandler();
        String res = handler.httpGet(latitude, longitude);

        // 输出或存库
        System.out.println(res);
    }

}
