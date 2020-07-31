package com.hujunchina.service.SQL.impl;

import com.hujunchina.core.dao.IRoleDAO;
import com.hujunchina.core.domain.RoleDO;
import com.hujunchina.service.SQL.SqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/30 10:55 下午
 * @Version 1.0
 */
@Service
public class SqlServiceImpl implements SqlService {

    @Resource
    IRoleDAO roleDAO;

    @Override
    public boolean addRole() {
        RoleDO role = new RoleDO();
        for (int i=100; i<2000; i++) {
            role.setUid(1+i*100);
            role.setRoleCode("code"+i*100);
            role.setRoleName("name"+i*100);
            role.setStatus(1);
            role.setTime(System.currentTimeMillis());
            roleDAO.insertRole(role);
        }
        return true;
    }
}
