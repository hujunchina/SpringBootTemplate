package com.hujunchina.service.beandemo;

import com.hujunchina.core.dao.IAdminDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author hujunchina@outlook.com
 * @date 2020-06-14
 */
@Component
@Slf4j
public class MyFactoryBean implements FactoryBean {

    @Resource
    IAdminDAO adminDAO;

    /** 自定义工厂类可以返回任何类型对象*/
    @Override
    public Object getObject() throws Exception {
        return adminDAO;
    }

    public void before(){
        log.info("MyFactoryBean 执行前");
    }

    public void after(){
        log.info("MyFacotryBean 执行后");
    }

    @Override
    public Class<?> getObjectType() {
        return IAdminDAO.class;
    }
}
