package com.hujunchina.client.pattern.A02AbstractFactory;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/5 2:09 下午
 * @Version 1.0
 */
public class CarC implements AbstractCarProduct{

    private String model;

    private String engine;

    private AbstractCarFactory carFactory = null;

    @Override
    public void productModel() {
        carFactory = new CarModelFactory();
        model = carFactory.productC();
    }

    @Override
    public void productEngine() {
        carFactory = new CarEngineFactory();
        engine = carFactory.productC();
    }

    @Override
    public String offline() {
        return model+engine;
    }
}
