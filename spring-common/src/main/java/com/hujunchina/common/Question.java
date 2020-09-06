package com.hujunchina.common;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/6 6:06 下午
 * @Version 1.0
 */
public @interface Question {
    String question() default "N";
    String condition() default "N";
    String solution() default "N";
}
