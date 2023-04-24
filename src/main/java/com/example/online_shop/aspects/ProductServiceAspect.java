package com.example.online_shop.aspects;

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
@Component
@Slf4j
public class ProductServiceAspect {

    @Around("com.example.online_shop.aspects.PointCuts.allProductServiceMethods()")
    public Object around(ProceedingJoinPoint joinPoint) {
        return logMethodStartAndFinish(joinPoint, log);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.ProductServiceImpl.create(*))",
            returning = "product")
    public void afterReturningWhenCreated(Product product) {
        String message = "Product with id = {}, name - {} and price - {} was created.";
        logProduct(product, message);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.ProductServiceImpl.delete(*))",
            returning = "product")
    public void afterReturningWhenDeleted(Product product) {
        String message = "Product with id = {}, name - {} and price - {} was deleted.";
        logProduct(product, message);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.ProductServiceImpl.editProduct(*))",
            returning = "product")
    public void afterReturningWhenEdited(Product product) {
        String message = "Product with id = {} was edited: new productName - {} and new productPrice = {}.";
        logProduct(product, message);
    }

    private static void logProduct(Product product, String message) {
        Long productId = product.getProductId();
        String productName = product.getName();
        Double productPrice = product.getPrice();
        log.info(message, productId, productName, productPrice);
    }

    @AfterThrowing("execution(* com.example.online_shop.service.impl.ProductServiceImpl.getById(Long))")
    public void afterThrowingInProductService(JoinPoint joinPoint) {
        Object productId = Arrays.stream(joinPoint.getArgs()).findFirst();
        Object methodName = joinPoint.getSignature().getName();
        log.warn("Method {} couldn't find product with id {}", methodName, productId);
    }
}
