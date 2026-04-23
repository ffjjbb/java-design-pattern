package com.fanjiabao.design.pattern.behavioral.responsibility.source_code_analysis;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/22 17:27
 * @description: 切面2
 */
@Aspect
@Component
public class Aspect2 {

    @Around("execution(* UserService.test(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Aspect2 前置");
        Object result = pjp.proceed();
        System.out.println("Aspect2 后置");
        return result;
    }

}
