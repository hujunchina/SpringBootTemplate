package com.hujunchina.core.domain;

import lombok.Data;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/30 10:34 下午
 * @Version 1.0
 */
@Data
public class RoleDO {

    private Integer id;
    private Integer uid;
    private String roleCode;
    private String roleName;
    private Integer status;
    private long time;
}
