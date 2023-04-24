package com.example.online_shop.aspects;

import com.example.online_shop.dto.responseDto.UserResponseDto;
import com.example.online_shop.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.example.online_shop.aspects.AllServicesLoggingAspect.logMethodStartAndFinish;

@Aspect
@Slf4j
@Component
public class UserServiceAspect {

    @Around("com.example.online_shop.aspects.PointCuts.allUserServiceMethods()")
    public Object around(ProceedingJoinPoint joinPoint) {
        return logMethodStartAndFinish(joinPoint, log);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.UserServiceImpl.create*(*))",
            returning = "user")
    public void afterReturningWhenCreated(UserResponseDto user) {
        String message = "Product with id = {}, name - {} and price - {} was created.";
        logProduct(product, message);
    }

    @AfterThrowing("execution(* com.example.online_shop.service.impl.UserServiceImpl.getUser(Long))")
    public void afterThrowingInProductService(JoinPoint joinPoint) {
        Object productId = Arrays.stream(joinPoint.getArgs()).findFirst();
        Object methodName = joinPoint.getSignature().getName();
        log.warn("Method {} couldn't find product with id {} and threw an exception.", methodName, productId);
    }
}
