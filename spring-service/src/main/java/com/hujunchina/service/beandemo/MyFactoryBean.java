package com.hujunchina.service.beandemo;

import com.hujunchina.core.dao.IAdminDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author hujunchina@outlook.com
 * @date 2020-06-14
 */
@Service
@Slf4j
public class MyFactoryBean implements FactoryBean {

    @Resource
    IAdminDAO adminDAO;

    @Override
    public Object getObject() throws Exception {
        Proxy.newProxyInstance(IAdminDAO.class.getClassLoader(), new Class[]{IAdminDAO.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                before();
                log.info("调用 proxy 类");
                after();
                return null;
            }
        });
        return null;
    }

    public void before(){
        log.info("MyFactoryBean 执行前");
    }

    public void after(){
        log.info("MyFacotryBean 执行后");
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
