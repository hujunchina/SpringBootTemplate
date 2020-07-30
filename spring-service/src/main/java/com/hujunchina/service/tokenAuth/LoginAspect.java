package com.hujunchina.service.tokenAuth;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/24 4:12 下午
 * @Version 1.0
 * 声明一个切面类
 */
@Aspect
@Component
@Slf4j
public class LoginAspect {

    @Pointcut(value = "execution(* com.hujunchina.service.*.*(..))")
    public void pointcut(){}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) {
        Object result = null;
        long beginTime = System.currentTimeMillis();
        try {
            // 执行切点方法
            result = point.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        saveLog(point, endTime - beginTime);
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
//        Log log = method.getAnnotation(Log.class);

        // 请求类名和方法名(就是签名)
        String className = joinPoint.getTarget().getClass().getName();
        log.info("【LoginAspect】class: {}", className);

        String methodName = signature.getName();
        log.info("【LoginAspect】method: {}", methodName);

        // 请求方法参数值
        Object[] args = joinPoint.getArgs();

        // 请求方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);

        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                params += " " + paramNames[i] + ": " + args[i];
            }
            log.info("【LoginAspect】params: {}", params);
        }

    }

}
