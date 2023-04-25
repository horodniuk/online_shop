package com.example.online_shop.controller;

import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.responseDto.OrderResponseDto;
import com.example.online_shop.dto.responseDto.UserResponseDto;
import com.example.online_shop.entity.Product;
import com.example.online_shop.service.OrderService;
import com.example.online_shop.service.UserService;
import jakarta.validation.Valid;
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

    // method needs to be implemented
    @PostMapping("/cart")
    public UserResponseDto addProductToCart(@RequestBody Product product) {
        return null;
    }

    // method needs to be implemented
    @DeleteMapping("/cart/{id}")
    public UserResponseDto deleteProduct(@PathVariable("id") Long userId, @RequestParam Long productId) {
        return null;
    }

    @GetMapping("/cart/{id}")
    public Map<Product, Integer> showAllProductsInCart(@PathVariable("id") Long userId) {
        return userService.showAllProductsInCart(userId);
    }

    @PostMapping("/cart/submit")
    public UserResponseDto addCartToOrder(@RequestBody UserRequestDto userRequestDto) {
        Long userId = userRequestDto.getUserId();
        return userService.addOrderToUser(userId);
    }

    @DeleteMapping("/cart")
    public UserResponseDto clearCart(@RequestBody UserRequestDto userRequestDto) {
        Long userId = userRequestDto.getUserId();
        return userService.clearCart(userId);
    }

    @GetMapping("/orders/{id}")
    public List<OrderResponseDto> getAllUserOrders(@PathVariable("id") Long userId) {
        return userService.getAllUserOrders(userId);
    }

    @GetMapping("/order/{id}")
    public OrderResponseDto getOrderById(@PathVariable("id") Long orderId) {
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
    public String addBalance(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.addBalance(userRequestDto);
    }
}
