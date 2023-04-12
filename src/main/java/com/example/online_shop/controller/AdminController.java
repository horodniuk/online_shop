package com.example.online_shop.controller;

import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.responseDto.UserResponseDto;
import com.example.online_shop.entity.Product;
import com.example.online_shop.exception.ProductNotFoundException;
import com.example.online_shop.service.ProductService;
import com.example.online_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ProductService productService;

    @DeleteMapping("/user/{id}")
    public UserResponseDto deleteUser(@PathVariable("id") Long userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping("/user")
    public UserResponseDto blockUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.editUser(userRequestDto);
    }

    @PostMapping("/user")
    public UserResponseDto addUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.addUser(userRequestDto);
    }

    @PostMapping("/product")
    public Product addProduct(@RequestBody Product product) {
        return productService.create(product);
    }

    @DeleteMapping("/product/{id}")
    public Product deleteProduct(@PathVariable("id") Long productId) {
        return productService.delete(productId);
    }

    @PutMapping("/product")
    public Product editProduct(@RequestBody Product product) {
        return productService.editProduct(product);
    }
}
