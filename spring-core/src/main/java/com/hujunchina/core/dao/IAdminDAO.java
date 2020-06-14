package com.hujunchina.core.dao;

import com.hujunchina.core.domain.AdminDO;

/**
 * @Author hujunchina@outlook.com
 * @date 2020-06-14
 */
public interface IAdminDAO {

    public AdminDO getAdminById(Integer id);

    public Boolean addAdmin(AdminDO adminDO);
}
