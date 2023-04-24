package com.example.online_shop.aspects;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {

    @Pointcut("execution(* com.example.online_shop.service.impl.ProductServiceImpl.*(..))")
    public void allProductServiceMethods() {
    }

    @Pointcut("execution(* com.example.online_shop.service.impl.OrderServiceImpl.*(..))")
    public void allOrderServiceMethods() {
    }

    @Pointcut("execution(* com.example.online_shop.service.impl.UserServiceImpl.*(..))")
    public void allUserServiceMethods() {
    }
}
