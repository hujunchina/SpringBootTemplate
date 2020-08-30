package com.hujunchina.client.pattern.A02AbstractFactory;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/5 2:00 下午
 * @Version 1.0
 */
public class CarModelFactory implements AbstractCarFactory{
    @Override
    public String productA() {
        return "几何A 车型";
    }

    @Override
    public String productC() {
        return "几何C 车型";
    }
}
