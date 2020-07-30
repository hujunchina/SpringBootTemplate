package com.hujunchina.service.beanDemo;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.FactoryBean;

import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/14 7:46 下午
 * @Version 1.0
 * 特殊的 Bean 用于生成 BeanFactory 的 Bean
 * 使用动态代理proxy的方式来实例化Bean，并执行方法
 */
public class RoleFactoryBean implements FactoryBean {

    private Class mapper;

    private static SqlSession sqlSession;

    private Map<Method, MapperMethod> methodCache;

    static{
        try {
            String resource = "mybaits-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = factory.openSession(true);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private MapperMethod cachedMapperMethod(Method method){
        if (methodCache == null) {
            methodCache = new HashMap<>();
        }
        MapperMethod mapperMethod = methodCache.get(method);
        if (mapperMethod == null) {
            mapperMethod = new MapperMethod(mapper, method, sqlSession.getConfiguration());
            methodCache.put(method, mapperMethod);
        }
        return mapperMethod;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public RoleFactoryBean(Class mapper){
        this.mapper = mapper;
    }

    @Override
    public Object getObject() throws Exception {
        Object o = Proxy.newProxyInstance(RoleFactoryBean.class.getClassLoader(), mapper.getClasses(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName());
                System.out.println(Object.class + "==" + method.getDeclaringClass());
                if (Object.class.equals(method.getDeclaringClass())) {
                    return method.invoke(this, args);
                } else {
                    MapperMethod mapperMethod = cachedMapperMethod(method);
                    return mapperMethod.execute(sqlSession, args);
                }
            }
        });
        return o;
    }

    @Override
    public Class<?> getObjectType() {
        return this.mapper;
    }
}
