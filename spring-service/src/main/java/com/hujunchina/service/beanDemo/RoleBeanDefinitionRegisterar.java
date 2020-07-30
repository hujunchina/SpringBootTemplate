package com.hujunchina.service.beanDemo;

import com.hujunchina.core.dao.IRoleDAO;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/14 10:37 下午
 * @Version 1.0
 */
public class RoleBeanDefinitionRegisterar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //【1】获取一个空的 BeanDefinition 对象
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        //【2】指定 BeanDefinition 类型
        beanDefinition.setBeanClass(RoleFactoryBean.class);
        //【3】指定构造方法的值
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(IRoleDAO.class);
        //【4】注册 BeanDefinition
        registry.registerBeanDefinition(IRoleDAO.class.getName(), beanDefinition);
    }
}
