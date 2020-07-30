package com.hujunchina.service.tokenAuth.impl;

import com.google.common.base.Strings;
import com.hujunchina.common.JWTUtils;
import com.hujunchina.common.ServiceException;
import com.hujunchina.common.ServiceResponseCode;
import com.hujunchina.manager.domain.TokenUserDTO;
import com.hujunchina.service.ServiceResponse;
import com.hujunchina.service.tokenAuth.TokenAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/1 1:37 下午
 * @Version 1.0
 */
@Slf4j
@Service
public class TokenAuthServiceImpl implements TokenAuthService {
    @Override
    public ServiceResponse<String> getToken(TokenUserDTO tokenUserDTO) {
        try {
            log.info("TokenAuthServiceImpl getToken tokenUserDTO: {0}", tokenUserDTO.toString());
            //【1】验证参数
            Assert.notNull(tokenUserDTO.getUserName(), "用户名不能为空");
            Assert.notNull(tokenUserDTO.getPassword(), "密码不能为空");

            //【2】判断是否有token带入
            if (!Strings.isNullOrEmpty(tokenUserDTO.getToken())){
                return ServiceResponse.asSuccess(ServiceResponseCode.SUCCESS, tokenUserDTO.getToken());
            }

            //【3】生成token
            String token = JWTUtils.createToken(tokenUserDTO.getUserName(), tokenUserDTO.getPassword());

            //【4】返回结果
            log.info("TokenAuthServiceImpl get token: {0}", token);
            return ServiceResponse.asSuccess(ServiceResponseCode.SUCCESS, token);

        } catch (Exception e){
            log.error("TokenAuthServiceImpl getToken failed", e);
            return ServiceResponse.asFail(ServiceResponseCode.FAILED, e.toString());
        }
    }

    @Override
    public ServiceResponse<String> refreshToken(TokenUserDTO tokenUserDTO) {
        return null;
    }

    @Override
    public boolean deleteToken(TokenUserDTO tokenUserDTO) {
        return false;
    }

    @Override
    public boolean checkToken(String token) {
        log.info("TokenAuthServiceImpl check token");
        return true;
    }
}
