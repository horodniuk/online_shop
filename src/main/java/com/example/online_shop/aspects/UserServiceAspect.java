package com.example.online_shop.aspects;

import com.example.online_shop.dto.responseDto.UserResponseDto;
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
        String message = "User with id = {}, firstName - {}, lastName - {}, email - {} and role - {} was created.";
        logUserChanges(user, message);
    }

    private static void logUserChanges(UserResponseDto user, String message) {
        Long userId = user.getUserId();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String role = user.getRole();
        log.info(message, userId, firstName, lastName, email, role);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.UserServiceImpl.deleteUser(*))",
            returning = "user")
    public void afterReturningWhenDeleted(UserResponseDto user) {
        String message = "User with id = {}, firstName - {}, lastName - {}, email - {} and role - {} was deleted.";
        logUserChanges(user, message);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.UserServiceImpl.editUser(*))",
            returning = "user")
    public void afterReturningWhenEdited(UserResponseDto user) {
        String message = "User with id = {} was changed. New firstName - {}, lastName - {}, email - {}. User role - {}";
        logUserChanges(user, message);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.UserServiceImpl.changeUserToAdmin(*))",
            returning = "user")
    public void afterReturningWhenUserToAdmin(UserResponseDto user) {
        String message = "Role for user with id = {} changed from USER to ADMIN.";
        logOrderChanges(user, message);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.UserServiceImpl.addOrderToUser(*))",
            returning = "user")
    public void afterReturningAddOrderToUser(UserResponseDto user) {
        String message = "Order was added from cart to user with id = {}.";
        logOrderChanges(user, message);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.UserServiceImpl.removeOrderFromUser(*))",
            returning = "user")
    public void afterReturningRemoveOrder(UserResponseDto user) {
        String message = "Order was removed from user with id = {}.";
        logOrderChanges(user, message);
    }

    private static void logOrderChanges(UserResponseDto user, String message) {
        Long userId = user.getUserId();
        log.info(message, userId);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.UserServiceImpl.addProductToCart(*))",
            returning = "user")
    public void afterReturningAddProductToCart(JoinPoint joinPoint, UserResponseDto user) {
        String message = "Product with id = {} was added to cart by User with id = {}.";
        logProductInCart(joinPoint, user, message);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.UserServiceImpl.removeProductFromCart(*))",
            returning = "user")
    public void afterReturningRemoveProductFromCart(JoinPoint joinPoint, UserResponseDto user) {
        String message = "Product with id = {} was removed from cart by User with id = {}.";
        logProductInCart(joinPoint, user, message);
    }

    private static void logProductInCart(JoinPoint joinPoint, UserResponseDto user, String message) {
        Long productId = (Long) Arrays.stream(joinPoint.getArgs()).findFirst().get();
        Long userId = user.getUserId();
        log.info(message, productId, userId);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.UserServiceImpl.addBalance(*))",
            returning = "message")
    public void afterReturningRemoveProductFromCart(String message) {
        log.info(message);
    }

    @AfterThrowing(value = "execution(* com.example.online_shop.aspects.PointCuts.allUserServiceMethods())",
            throwing = "exception")
    public void afterThrowingInUserService(Throwable exception) {
        log.warn(exception.getMessage());
    }
}
