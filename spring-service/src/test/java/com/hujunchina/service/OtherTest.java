package com.hujunchina.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
}
