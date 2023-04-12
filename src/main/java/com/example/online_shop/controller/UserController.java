package com.example.online_shop.controller;

import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.responseDto.OrderResponseDto;
import com.example.online_shop.dto.responseDto.UserResponseDto;
import com.example.online_shop.entity.Product;
import com.example.online_shop.service.OrderService;
import com.example.online_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @PostMapping("/cart")
    public UserResponseDto addProductToCart(@RequestBody Product product) {

    }

    @DeleteMapping("/cart/{id}")
    public UserResponseDto deleteProduct(@PathVariable) {


    }

    @GetMapping("/cart/{id}")
    public Map<Product, Integer> showAllProductsInCart(@PathVariable("id") Long userId) {
        return userService.showAllProductsInCart(userId);
    }

    @PostMapping("/cart/submit")
    public UserResponseDto addCartToOrder(@RequestBody UserRequestDto userRequestDto) {
        Long userId = userRequestDto.getUserId();
        return userService.addOrderFromCart(userId);
    }

    @DeleteMapping("/cart")
    public UserResponseDto clearCart(@RequestBody UserRequestDto userRequestDto) {
        Long userId = userRequestDto.getUserId();
        return userService.clearCart(userId);
    }

    @GetMapping("/orders")
    public List<OrderResponseDto> getAllUserOrders(@RequestBody UserRequestDto userRequestDto) {
        Long userId = userRequestDto.getUserId();
        return userService.getAllUserOrders(userId);
    }

    @GetMapping("/orders/{id}")
    public OrderResponseDto getOrderById(@PathVariable("id") Long orderId) {
        return orderService.findById(orderId);
    }

    @PostMapping("/register")
    public UserResponseDto registerUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.addUser(userRequestDto);
    }

    @PostMapping("/login")
    public UserResponseDto loginUser() {

    }

    @PutMapping("/profile")
    public UserResponseDto editProfile(@RequestBody UserRequestDto userRequestDto) {
        return userService.editUser(userRequestDto);
    }

    @PutMapping("/balance")
    public String addBalance(@RequestBody UserRequestDto userRequestDto) {
        return userService.addBalance(userRequestDto);
    }

}
