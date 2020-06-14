package com.hujunchina.service;

import com.hujunchina.core.dao.IAdminDAO;
import com.hujunchina.core.domain.AdminDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author hujunchina@outlook.com
 * @date 2020-06-14
 */
@Service
public class AdminService {

    @Resource
    IAdminDAO adminDAO;

    public List<AdminDO> getAdminList(){
        return adminDAO.selectList(null);
    }

}
