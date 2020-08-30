package com.hujunchina.client.swordoffer;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/26 10:39 下午
 * @Version 1.0
 * 左旋转字符串
 */
public class A58_2 {
    public static void main(String[] args) {
        System.out.println(reverseStr("hujun", 2));
    }

    public static String reverseStr(String str, int cutpoint) {
        //from：to，省略默认是len的长度，默认左闭又开
        return str.substring(cutpoint) + str.substring(0, cutpoint);

    }
}
