package com.hujunchina.core.dao.impl;

import com.hujunchina.core.dao.IRoleDAO;
import com.hujunchina.core.domain.RoleDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/30 10:08 下午
 * @Version 1.0
 */
@Repository
public class RoleDAO implements IRoleDAO {
    @Override
    public int insertRole(RoleDO role) {

        return 0;
    }

    @Override
    public int insertRoleList(List<RoleDO> roles) {
        return 0;
    }

    @Override
    public int insertRoleForList(List<RoleDO> roles) {
        return 0;
    }

    @Override
    public int updateRole(RoleDO ro) {
        return 0;
    }

    @Override
    public int removeRole(String uid) {
        return 0;
    }
}
