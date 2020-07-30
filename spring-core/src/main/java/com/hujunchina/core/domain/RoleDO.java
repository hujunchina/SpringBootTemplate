package com.hujunchina.core.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/14 2:17 下午
 * @Version 1.0
 */
@Data
@Repository("role")
public class RoleDO implements Serializable {

    private Integer id;

    private String uid;

    private String roleCode;

    private String roleName;

    private Integer status;
}
