package com.hujunchina.client.pattern.A01FactoryMethod;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/5 12:01 下午
 * @Version 1.0
 * 工厂方法模式（创建型）：通过工厂类创建实例，而不是构造方法
 * 通过一个hashmap 维护工厂生产的产品
 * 实现了一个简单的水果生产工厂，只需要调用工厂的生产方法并传入水果的名称
 * 就可以生产出对应的水果实例
 */
public class FactoryMethodTest {

    public static void main(String[] args) {
        // 工厂类
        FruitFactory fruitFactory = new FruitFactory();
        // 初始化工厂
        fruitFactory.init();
        // 生产水果，指定为苹果
        Fruit fruit = fruitFactory.product("apple");
        // 苹果生长
        System.out.println(fruit.reproduce());
    }

}
