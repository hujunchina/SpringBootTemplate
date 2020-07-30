package com.hujunchina.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hujunchina.core.domain.AdminDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author hujunchina@outlook.com
 * @date 2020-06-14
 */
public interface IAdminDAO extends BaseMapper<AdminDO> {

    public AdminDO getAdminById(Integer id);

    public Boolean addAdmin(AdminDO adminDO);
}
