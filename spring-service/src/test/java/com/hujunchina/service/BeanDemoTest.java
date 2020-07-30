package com.hujunchina.service;

import com.hujunchina.service.beanDemo.MyFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试类
 * @Author hujunchina@outlook.com
 * @Date 2020-06-16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath: applicationContext.xml")
public class BeanDemoTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void beanTest(){
//        MyFactoryBean factoryBean = (MyFactoryBean) context.getBean("MyFactoryBean");
//        factoryBean.after();
    }

}
