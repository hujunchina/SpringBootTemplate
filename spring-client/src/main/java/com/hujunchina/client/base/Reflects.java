package com.hujunchina.client.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/3 5:24 下午
 * @Version 1.0
 * Java反射生成类
 */
public class Reflects {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        getClass01();
    }

    public static void getClass01() throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class c = Class.forName("com.hujunchina.client.base.BaseType");
        Method[] m = c.getMethods();
        for(Method i : m){
            System.out.println(i.getName());
            if(i.getName().equals("parameterVariable")){
                Object o = c.newInstance();
                //原类是可变长变量，处理类是一个数组
                i.invoke(o, 1, new String[]{"hujun", "hujun"});
            }
        }
    }
}
