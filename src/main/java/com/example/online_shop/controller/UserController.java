package com.example.online_shop.controller;

import com.example.online_shop.dto.requestDto.OrderRequestDto;
import com.example.online_shop.dto.requestDto.OrderInfoRequestDto;
import com.example.online_shop.dto.requestDto.UserIdRequestDto;
import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.responseDto.OrderResponseDto;
import com.example.online_shop.dto.responseDto.UserResponseDto;
import com.example.online_shop.entity.Order;
import com.example.online_shop.service.OrderService;
import com.example.online_shop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @PostMapping("/order")
    public UserResponseDto addOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return userService.addOrder(orderRequestDto);

    }

    @DeleteMapping("/order")
    public UserResponseDto removeOrder(@RequestBody OrderInfoRequestDto orderRequestDto) {
        return userService.removeOrder(orderRequestDto);
    }

    @GetMapping("/orders/{id}")
    public List<OrderResponseDto> findAllOrdersByUserId(@PathVariable("id") Long userId) {
        return userService.findAllOrdersByUserId(userId);
    }

    @GetMapping("/order/{id}")
    public OrderResponseDto findById(@PathVariable("id") Long orderId) {
        return orderService.findById(orderId);
    }

    @PostMapping("/register")
    public UserResponseDto registerUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @PutMapping("/profile")
    public UserResponseDto editProfile(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.editUser(userRequestDto);
    }

    @PutMapping("/balance")
    public String addBalance(@RequestBody @Valid UserIdRequestDto userIdRequestDto) {
        return userService.addBalance(userIdRequestDto);
    }
}
