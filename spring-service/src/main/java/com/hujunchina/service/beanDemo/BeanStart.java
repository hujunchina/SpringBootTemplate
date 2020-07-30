package com.hujunchina.service.beanDemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/14 10:34 下午
 * @Version 1.0
 * spring boot 配置访问其他模块包中的mapper和xml?
 */
@MapperScan(basePackages = {"com.hujunchina.manager"})
public class BeanStart {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanAppConfig.class);
        RoleService service = (RoleService) context.getBean("roleService", RoleService.class);
//        service.addRole();

        System.out.println("====spring end====");
        System.exit(0);
    }
}
