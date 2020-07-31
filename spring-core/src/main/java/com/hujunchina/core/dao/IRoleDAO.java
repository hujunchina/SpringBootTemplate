package com.hujunchina.core.dao;

import com.hujunchina.core.domain.RoleDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/30 10:35 下午
 * @Version 1.0
 */
@Repository
@Mapper
public interface IRoleDAO {

    int insertRole(RoleDO role);

    int insertRoleList(List<RoleDO> roles);

    int insertRoleList2(List<RoleDO> roles);
}
