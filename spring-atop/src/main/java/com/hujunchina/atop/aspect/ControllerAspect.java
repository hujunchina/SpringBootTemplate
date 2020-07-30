package com.hujunchina.atop.aspect;

import com.hujunchina.common.EdgeException;
import com.hujunchina.common.ServiceException;
import com.hujunchina.service.tokenAuth.TokenAuthService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/24 10:12 下午
 * @Version 1.0
 * 建立一个拦截器，对每个访问都校验token
 */
@Aspect
@Slf4j
public class ControllerAspect {

    @Autowired
    private TokenAuthService tokenAuthService;

    @Around(value = "execution(* com.hujunchina.controller.*.*(..))")
    public void checkToken(ProceedingJoinPoint pj) throws Throwable {
        Object[] args = pj.getArgs();
        boolean res = tokenAuthService.checkToken(args[0].toString());
        if (!res) {
            throw new EdgeException("token 非法");
        } else {
            log.info("token: {} 有效", args[0].toString());
            Object ans = pj.proceed();
        }

    }
}
