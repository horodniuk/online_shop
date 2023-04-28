package com.example.online_shop.aspects;

import com.example.online_shop.dto.responseDto.UserResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class UserServiceAspect {

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.UserServiceImpl.create*(*))",
            returning = "user")
    public void afterReturningWhenCreated(UserResponseDto user) {
        var message = "User with id = {}, firstName - {}, lastName - {}, email - {} was created.";
        logUserChanges(user, message);
    }

    private static void logUserChanges(UserResponseDto user, String message) {
        Long userId = user.getUserId();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        log.info(message, userId, firstName, lastName, email);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.UserServiceImpl.deleteUser(*))",
            returning = "user")
    public void afterReturningWhenDeleted(UserResponseDto user) {
        var message = "User with id = {}, firstName - {}, lastName - {}, email - {} was deleted.";
        logUserChanges(user, message);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.UserServiceImpl.editUser(*))",
            returning = "user")
    public void afterReturningWhenEdited(UserResponseDto user) {
        var message = "User with id = {} was changed. New firstName - {}, lastName - {}, email - {}.";
        logUserChanges(user, message);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.UserServiceImpl.changeUserToAdmin(*))",
            returning = "user")
    public void afterReturningWhenUserToAdmin(UserResponseDto user) {
        var message = "Role for user with id = {} changed from USER to ADMIN.";
        Long userId = user.getUserId();
        log.info(message, userId);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.UserServiceImpl.addOrder(*))",
            returning = "user")
    public void afterReturningAddOrder(UserResponseDto user) {
        var message = "Order was added to User with id = {}.";
        logOrder(user, message);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.UserServiceImpl.removeOrder(*))",
            returning = "user")
    public void afterReturningRemoveOrder(UserResponseDto user) {
        var message = "Order was removed from User with id = {}.";
        logOrder(user, message);
    }

    private static void logOrder(UserResponseDto user, String message) {
        Long userId = user.getUserId();
        log.info(message, userId);
    }

    @AfterReturning(value = "execution(* com.example.online_shop.service.impl.UserServiceImpl.addBalance(*))",
            returning = "message")
    public void afterReturningRemoveProductFromCart(String message) {
        log.info(message);
    }

    @AfterThrowing(value = "execution(* com.example.online_shop.service.impl.UserServiceImpl.*(..))",
            throwing = "exception")
    public void afterThrowingInUserService(Throwable exception) {
        log.warn(exception.getMessage());
    }
}
