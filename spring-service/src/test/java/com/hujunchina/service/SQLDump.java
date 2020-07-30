package com.hujunchina.service;

import com.hujunchina.core.dao.IRoleDAO;
import com.hujunchina.core.domain.RoleDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/30 9:40 下午
 * @Version 1.0
 * 生成数据
 */
@Slf4j
@SpringBootTest
@ContextConfiguration(value = "classpath:applicationContext.xml")
@RunWith(SpringRunner.class)
public class SQLDump {

    @Resource
    IRoleDAO roleDAO;

    @Test
    public void generateData(){
        Long start = System.currentTimeMillis();
        RoleDO role = new RoleDO();
        for (int i=1; i<100; i++) {
            role.setStatus(1);
            role.setRoleName("role"+i*100);
            role.setRoleCode(String.valueOf(i*100));
            role.setUid(String.valueOf(i));
            roleDAO.insertRole(role);
        }
        Long end = System.currentTimeMillis();

        log.info("escape time : {}", end-start);

    }

}
