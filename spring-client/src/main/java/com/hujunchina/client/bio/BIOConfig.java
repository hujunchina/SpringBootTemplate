package com.hujunchina.client.bio;

import java.io.Serializable;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/30 4:15 下午
 * @Version 1.0
 */
public class BIOConfig implements Serializable {

    //在连接的解释环节，会在方法区初始化静态变量，final的直接赋值
    private static final int PORT = 7250;
    private static final String HOST = "49.235.91.89";

    public static int getPort(){
        return PORT;
    }

    public static String getHost(){
        return HOST;
    }
}
