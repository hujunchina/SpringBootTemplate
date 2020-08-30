package com.hujunchina.client.pattern.A02AbstractFactory;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/5 12:13 下午
 * @Version 1.0
 * 抽象工厂（生产型）：不同的工厂生产不同产品的组件，最后组合起来。
 * 实现一个抽象汽车工厂，有车型生产工厂和电池电机生产工厂
 * 根据传入的参数组合得到相应的汽车。
 * 抽象工厂方法一个特点是一次需要创建多个对象，
 * 如CarA对象需要同时创建引擎对象和车型对象，才能成为车。
 * 所以CarA使用了组合，多个工厂
 * 而工厂方法使用了继承，只根据类型生产一个对象。
 */
public class AbstractFactoryTest {

    public static void main(String[] args) {
        CarA a = new CarA();
        a.productEngine();
        a.productModel();
        System.out.println(a.offline());

        CarC c = new CarC();
        c.productEngine();
        c.productModel();
        System.out.println(c.offline());
    }
}
