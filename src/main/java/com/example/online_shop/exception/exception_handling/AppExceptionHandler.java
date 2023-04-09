package com.example.online_shop.exception.exception_handling;

import com.example.online_shop.exception.OrderNotFoundException;
import com.example.online_shop.exception.ProductNotFoundException;
import com.example.online_shop.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionMessageContainer> handleProductException(ProductNotFoundException exception) {
        ExceptionMessageContainer messageContainer = new ExceptionMessageContainer();
        messageContainer.setMessage(exception.getMessage());
        return new ResponseEntity<>(messageContainer, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionMessageContainer> handleUserException(UserNotFoundException exception) {
        ExceptionMessageContainer messageContainer = new ExceptionMessageContainer();
        messageContainer.setMessage(exception.getMessage());
        return new ResponseEntity<>(messageContainer,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionMessageContainer> handleOrderException(OrderNotFoundException exception) {
        ExceptionMessageContainer messageContainer = new ExceptionMessageContainer();
        messageContainer.setMessage(exception.getMessage());
        return new ResponseEntity<>(messageContainer,HttpStatus.NOT_FOUND);
    }
}
