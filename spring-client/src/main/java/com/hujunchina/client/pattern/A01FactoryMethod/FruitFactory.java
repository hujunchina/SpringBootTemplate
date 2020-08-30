package com.hujunchina.client.pattern.A01FactoryMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/5 9:33 上午
 * @Version 1.0
 */
public class FruitFactory {

    private Map<String, Fruit> fruits = new HashMap<>();

    public void init(){
        fruits.put("apple", new AppleFruit());
        fruits.put("peach", new PeachFruit());
    }

    public Fruit product(String fruit) {
        return fruits.get(fruit);
    }

}
