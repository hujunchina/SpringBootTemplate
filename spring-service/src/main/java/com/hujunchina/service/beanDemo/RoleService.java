package com.hujunchina.service.beanDemo;

import com.hujunchina.core.dao.IRoleDAO;
import com.hujunchina.core.domain.RoleDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/14 7:37 下午
 * @Version 1.0
 */
@Service("roleService")
public class RoleService {

//    @Resource
//    private IRoleDAO roleDAO;
//
//    public void addRole(){
//        RoleDO role = new RoleDO();
//        role.setRoleCode("role code");
//        role.setRoleName("role name");
//        role.setUid(UUID.randomUUID().toString());
//        role.setStatus(0);
//        System.out.println(roleDAO.insertRole(role));
//    }
//
//    public void addRole(RoleDO role){
//        System.out.println(roleDAO.insertRole(role));
//    }
//
//    public void insertRoleList(List<RoleDO> roleDOList){
//        int ret = roleDAO.insertRoleList(roleDOList);
//        System.out.println(ret);
//    }
//
//    public void insertRoleForList(List<RoleDO> roleDOList){
//        int res = roleDAO.insertRoleForList(roleDOList);
//        System.out.println(res);
//    }
}
