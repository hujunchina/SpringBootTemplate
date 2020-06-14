package com.hujunchina.core.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @Author hujunchina@outlook.com
 * @date 2020-06-14
 */
@TableName("kunlun_admin")
public class AdminDO {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String password;
}
