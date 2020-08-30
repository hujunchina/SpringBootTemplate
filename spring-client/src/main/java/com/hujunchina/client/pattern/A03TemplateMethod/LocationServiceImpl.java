package com.hujunchina.client.pattern.A03TemplateMethod;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/6 9:43 上午
 * @Version 1.0
 * 延迟实现定位服务，三方具体实现
 * 对定位数据进行解密，有偏移量影响
 * 两种方式：1. 直接给固定类名，让三方实现方法即可
 *         2. 让三方实现类，但需要把类名给SDK，通过反射实例化
 */
public class LocationServiceImpl implements LocationService{
    @Override
    public String location(Long latitude, Long longitude) {
        Long offset = 12L;
        Long codeX = latitude - offset;
        Long codeY = longitude - offset;
        String s = String.format("三方修复的定位服务, codeX=%d, codeY=%d", codeX, codeY);
        return s;
    }
}
