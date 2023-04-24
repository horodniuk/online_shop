package com.example.online_shop.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;

public class AllServicesLoggingAspect {

    protected static Object logMethodStartAndFinish(ProceedingJoinPoint joinPoint, Logger log) {
        String methodName = joinPoint.getSignature().getName();
        log.trace("Method {} is starting", methodName);
        Object targetMethodResult = null;
        try {
            targetMethodResult = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        log.trace("Method {} is finishing", methodName);

        return targetMethodResult;
    }
}
