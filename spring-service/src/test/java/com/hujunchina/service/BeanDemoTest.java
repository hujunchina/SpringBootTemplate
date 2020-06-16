package com.hujunchina.service;

import com.hujunchina.SpringStarter;
import com.hujunchina.service.beandemo.MyFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringStarter.class)
public class BeanDemoTest {

    @Autowired
    MyFactoryBean factoryBean;

    @Test
    public void beanTest(){

    }

}
