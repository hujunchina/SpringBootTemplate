package com.hujunchina.controller;

import com.hujunchina.service.tokenAuth.MyLog;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/24 5:35 下午
 * @Version 1.0
 * 为了测试切面的登录器
 */

@RestController
@Slf4j
public class LoginController {

    @MyLog("执行用户登录")
    @GetMapping("/login")
    public String login(String username, String password, String token){
        log.info("token");
        return token;
    }
}
