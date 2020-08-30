package com.hujunchina.client.pattern.A07Builder;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/16 4:09 下午
 * @Version 1.0
 * 建造者模式（生成类）：通过分部构造成员变量来实例化类
 * 每次调用传入一个成员变量，并返回自身
 * 例子：通过分部构造不同类型的订单
 */
public class BuilderTest {
    public static void main(String[] args) {
        DealDO deal = DealBuilder.
                getDealBuilder().
                withId(123).
                withSn("SN39").
                withPlace("location").
                withType("type").getDeal();

        System.out.println(deal.toString());
    }
}
