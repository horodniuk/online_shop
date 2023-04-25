package com.example.online_shop.aspects;

import com.example.online_shop.dto.responseDto.OrderResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@Aspect
public class OrderServiceAspect {

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

    @AfterThrowing(value = "execution(* com.example.online_shop.service.impl.OrderServiceImpl.*(..))",
            throwing = "exception")
    public void afterThrowing(Throwable exception) {
        log.warn(exception.getMessage());
    }
}
