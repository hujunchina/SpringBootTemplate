package com.hujunchina.core.dao;

import com.hujunchina.core.domain.RoleDO;

import java.util.List;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/14 2:17 下午
 * @Version 1.0
 */
public interface IRoleDAO {

    int insertRole(RoleDO role);

    int insertRoleList(List<RoleDO> roles);

    int insertRoleForList(List<RoleDO> roles);

    int updateRole(RoleDO ro);

    int removeRole(String uid);
}
