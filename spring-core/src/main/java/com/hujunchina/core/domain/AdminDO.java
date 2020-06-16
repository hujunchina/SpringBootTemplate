package com.hujunchina.core.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.Bean;

/**
 * @Author hujunchina@outlook.com
 * @date 2020-06-14
 */
@TableName("kunlun_admin")
@Data
public class AdminDO {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String password;
}
