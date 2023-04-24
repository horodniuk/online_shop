package com.example.online_shop.aspects;

import com.example.online_shop.dto.responseDto.OrderResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.OrderServiceImpl.saveOrder(*))",
            returning = "order")
    public void afterReturningWhenCreated(OrderResponseDto order) {
        String message = "Order with id = {}, date - {} and userId - {} was created.";
        logOrder(order, message);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.OrderServiceImpl.deleteOrder(*))",
            returning = "order")
    public void afterReturningWhenDeleted(OrderResponseDto order) {
        String message = "Order with id = {}, date - {} and userId - {} was deleted.";
        logOrder(order, message);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.OrderServiceImpl.editOrder(*))",
            returning = "order")
    public void afterReturningWhenEdited(OrderResponseDto order) {
        String message = "Order with id = {}, date - {} and userId - {} was edited.";
        logOrder(order, message);
    }

    private static void logOrder(OrderResponseDto order, String message) {
        Long orderId = order.getOrderId();
        LocalDateTime date = order.getOrderDate();
        Long userId = order.getUserId();
        log.info(message, orderId, date, userId);
    }

    @AfterThrowing("execution(* com.example.online_shop.service.impl.OrderServiceImpl.getOrder(Long))")
    public void afterThrowing(JoinPoint joinPoint) {
        Object orderId = Arrays.stream(joinPoint.getArgs()).findFirst();
        Object methodName = joinPoint.getSignature().getName();
        log.warn("Method {} couldn't find order with id {} and threw an exception", methodName, orderId);
    }
}
