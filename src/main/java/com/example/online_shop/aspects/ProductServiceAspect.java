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

@Aspect
@Component
@Slf4j
public class ProductServiceAspect {


    @Around("com.example.online_shop.aspects.PointCuts.allProductServiceMethods()")
    public Object around(ProceedingJoinPoint joinPoint) {
        return logMethodStartAndFinish(joinPoint, log);
    }

    @AfterThrowing("execution(* com.example.online_shop.service.impl.ProductServiceImpl.getById(Long))")
    public void afterThrowingInProductService(JoinPoint joinPoint) {
        Object productId = Arrays.stream(joinPoint.getArgs()).findFirst();
        Object methodName = joinPoint.getSignature().getName();
        log.warn("Method {} couldn't find product with id {}", methodName, productId);
    }
}
