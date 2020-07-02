package com.hujunchina.manager.domain;

import lombok.Data;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/1 1:38 下午
 * @Version 1.0
 */

@Data
public class TokenUserDTO {

    /** 用户姓名*/
    private String userName;

    /** 用户姓名*/
    private String password;

    /** token需要及时封装*/
    private String token;
}
