package com.hujunchina.service.beanDemo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/14 10:36 下午
 * @Version 1.0
 * spring 配置文件，写为类了
 */
@Configuration
@ComponentScan("com.hujunchina.service.beanDemo")
@Import(RoleBeanDefinitionRegisterar.class)
public class BeanAppConfig {

}
