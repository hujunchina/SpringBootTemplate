package com.hujunchina.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/6/20 10:22 上午
 * @Version 1.0
 */
@Slf4j
public class OtherTest {

    @Test
    public void StringTest(){
        String result = String.format("hujun", "love","liujihong");
        log.info(result);
    }

    @Test
    public void JsonTest(){
        HashMap<String, Object> data = new HashMap<>();
        data.put("device_id", "EF9032");
        // 把一个类对象转为一个JSON，只是这个JSON在java 中是以String类型存在！！
        String jsonStr = JSON.toJSONString(data);
        log.info(jsonStr);

        // 把JSON 转为一个类对象并使用
        Integer in = JSON.parseObject("123321", Integer.class);
        log.info(String.valueOf(in));

        // 把JSON转为一个对象，对没有特定类对象使用时
        JSONObject jsonObject = JSON.parseObject("{'name':'hujun','use':'test'}");
        String name = jsonObject.getString("name");
        String use = jsonObject.getString("use");
        log.info(name+use);
    }

    @Test
    public void StreamTest(){
        List<String> arr = new ArrayList<>();
        arr.add("hujun");
        arr.add("test");
        arr.add("123");
        // 对每个元素进行流处理
        List<String> Arr = arr.stream().map(res->{
            System.out.println(res);
            return res+"1";
        }).collect(Collectors.toList());
        System.out.println(Arr.toString());
    }
}
