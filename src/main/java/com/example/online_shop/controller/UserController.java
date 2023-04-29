package com.example.online_shop.controller;

import com.example.online_shop.dto.requestDto.OrderRequestDto;
import com.example.online_shop.dto.requestDto.OrderInfoRequestDto;
import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.responseDto.OrderResponseDto;
import com.example.online_shop.dto.responseDto.UserInfoResponseDto;
import com.example.online_shop.dto.responseDto.UserResponseDto;
import com.example.online_shop.service.OrderService;
import com.example.online_shop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/order/{id}")
    public OrderResponseDto getOrderById(@PathVariable("id") Long orderId) {
        return orderService.findById(orderId);
    }

    @PostMapping("/register")
    public UserInfoResponseDto registerUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @PutMapping("/profile")
    public UserInfoResponseDto editProfile(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.editUser(userRequestDto);
    }

    @PutMapping("/balance")
    public String addBalance(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.addBalance(userRequestDto);
    }
}
