package com.hujunchina.service.tokenAuth;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/24 5:48 下午
 * @Version 1.0
 * 自定义注解日志
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyLog {
    String value() default "L";
}
