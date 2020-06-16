package com.hujunchina.core.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hujunchina.core.dao.IAdminDAO;
import com.hujunchina.core.domain.AdminDO;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * @Author hujunchina@outlook.com
 * @date 2020-06-14
 */
@Repository
public class AdminDAO implements IAdminDAO {

    @Override
    public AdminDO getAdminById(Integer id) {
        return null;
    }

    @Override
    public Boolean addAdmin(AdminDO adminDO) {
        return null;
    }

    @Override
    public int insert(AdminDO entity) {
        return 0;
    }

    @Override
    public int deleteById(Serializable id) {
        return 0;
    }

    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        return 0;
    }

    @Override
    public int delete(Wrapper<AdminDO> wrapper) {
        return 0;
    }

    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        return 0;
    }

    @Override
    public int updateById(AdminDO entity) {
        return 0;
    }

    @Override
    public int update(AdminDO entity, Wrapper<AdminDO> updateWrapper) {
        return 0;
    }

    @Override
    public AdminDO selectById(Serializable id) {
        return null;
    }

    @Override
    public List<AdminDO> selectBatchIds(Collection<? extends Serializable> idList) {
        return null;
    }

    @Override
    public List<AdminDO> selectByMap(Map<String, Object> columnMap) {
        return null;
    }

    @Override
    public AdminDO selectOne(Wrapper<AdminDO> queryWrapper) {
        return null;
    }

    @Override
    public Integer selectCount(Wrapper<AdminDO> queryWrapper) {
        return null;
    }

    @Override
    public List<AdminDO> selectList(Wrapper<AdminDO> queryWrapper) {
        return null;
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<AdminDO> queryWrapper) {
        return null;
    }

    @Override
    public List<Object> selectObjs(Wrapper<AdminDO> queryWrapper) {
        return null;
    }

    @Override
    public <E extends IPage<AdminDO>> E selectPage(E page, Wrapper<AdminDO> queryWrapper) {
        return null;
    }

    @Override
    public <E extends IPage<Map<String, Object>>> E selectMapsPage(E page, Wrapper<AdminDO> queryWrapper) {
        return null;
    }
}
