package com.hujunchina.controller;

import com.google.inject.internal.cglib.core.$ReflectUtils;
import com.hujunchina.common.ServiceResponseCode;
import com.hujunchina.core.dao.IRoleDAO;
import com.hujunchina.core.domain.RoleDO;
import com.hujunchina.manager.domain.TokenUserDTO;
import com.hujunchina.service.ServiceResponse;
import com.hujunchina.service.tokenAuth.TokenAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hujunchina@outlook.com
 * @date 2020-06-14
 *
 * 当使用 @Controller 时无法访问，因为String返回的是视图的文件名
 * 需要使用 @RestController 返回自带 json 格式的数据！！
 */
@RestController
@RequestMapping("/")
@Slf4j
public class MainController {

    @Resource
    TokenAuthService tokenAuthService;

    @Resource
    IRoleDAO roleDAO;

    @GetMapping("/index")
    public String index(){
        return "Hello Spring!";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "Hello SpringBoot !";
    }

    // http://localhost:9090/token-auth?userName=hujun&password=123456
    @RequestMapping("/token-auth")
    public ServiceResponse<String> tokenAuth(String userName, String password, String token) {
        TokenUserDTO tokenUserDTO = new TokenUserDTO();
        tokenUserDTO.setUserName(userName);
        tokenUserDTO.setPassword(password);
        tokenUserDTO.setToken(token);
        ServiceResponse<String> result = tokenAuthService.getToken(tokenUserDTO);
        return result;
    }

    @GetMapping("sql")
    public ServiceResponse<String> sql() {
        Long start = System.currentTimeMillis();
        RoleDO role = new RoleDO();
        for (int i=1; i<100; i++) {
            role.setStatus(1);
            role.setRoleName("role"+i*100);
            role.setRoleCode(String.valueOf(i*100));
            role.setUid(String.valueOf(i));
            roleDAO.insertRole(role);
        }
        Long end = System.currentTimeMillis();

        log.info("escape time : {}", end-start);
        return ServiceResponse.asSuccess(ServiceResponseCode.SUCCESS, String.valueOf(end-start));
    }
}
