package com.hujunchina.client.swordoffer;

import java.util.Arrays;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/28 10:03 下午
 * @Version 1.0
 * 输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。
 * 为简单起见，标点符号和普通字母一样处理。例如输入字符串"I am a student. "，
 * 则输出"student. a am I"。
 */
public class A58 {
    public static void main(String[] args) {
        String s = " hello world! ";
        reverseWords(s);
    }

    //使用正则匹配，速度很慢
    public static String reverseWords(String s) {
        s = s.trim();   //去除首尾空格
        String[] token = s.split("\\s+"); //匹配空格
        System.out.println(token.length+Arrays.toString(token));
        StringBuilder sb = new StringBuilder();
        for(int i=token.length-1; i>=0; i--){
            sb.append(token[i]);
            if(i!=0){
                sb.append(" ");
            }
        }
        System.out.println(sb.toString());
        return s;
    }

    //
}
