package com.hujunchina.service.tokenAuth;

import com.hujunchina.manager.domain.TokenUserDTO;
import com.hujunchina.service.ServiceResponse;
import org.springframework.stereotype.Service;
import sun.tools.jstat.Token;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/1 1:36 下午
 * @Version 1.0
 */
public interface TokenAuthService {

    ServiceResponse<String> getToken(TokenUserDTO tokenUserDTO);

    ServiceResponse<String> refreshToken(TokenUserDTO tokenUserDTO);

    boolean deleteToken(TokenUserDTO tokenUserDTO);
}
