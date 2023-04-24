package com.example.online_shop.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.example.online_shop.aspects.AllServicesLoggingAspect.logMethodStartAndFinish;

@Slf4j
@Component
@Aspect
public class OrderServiceAspect {

    @Around("com.example.online_shop.aspects.PointCuts.allOrderServiceMethods()")
    public Object around(ProceedingJoinPoint joinPoint) {
        return logMethodStartAndFinish(joinPoint, log);
    }



    @AfterThrowing("execution(* com.example.online_shop.service.impl.OrderServiceImpl.getOrder(Long))")
    public void afterThrowing(JoinPoint joinPoint) {
        Object orderId = Arrays.stream(joinPoint.getArgs()).findFirst();
        Object methodName = joinPoint.getSignature().getName();
        log.warn("Method {} couldn't find order with id {}", methodName, orderId);
    }
}
