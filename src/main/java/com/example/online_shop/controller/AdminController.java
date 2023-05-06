package com.example.online_shop.controller;

import com.example.online_shop.dto.requestDto.ProductRequestDto;
import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.responseDto.UserInfoResponseDto;
import com.example.online_shop.dto.responseDto.UserResponseDto;
import com.example.online_shop.entity.Product;
import com.example.online_shop.service.ProductService;
import com.example.online_shop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ProductService productService;

    @DeleteMapping("/user/{id}")
    public UserInfoResponseDto deleteUser(@PathVariable("id") Long userId) {
        return userService.deleteUser(userId);
    }

    @PostMapping("/user")
    public UserInfoResponseDto addUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @PostMapping("/product")
    public Product addProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        return productService.create(productRequestDto);
    }

    @DeleteMapping("/product/{id}")
    public Product deleteProduct(@PathVariable("id") Long productId) {
        return productService.delete(productId);
    }

    @PutMapping("/product")
    public Product editProduct(@RequestBody @Valid Product product) {
        return productService.editProduct(product);
    }

    @PutMapping("/user/block/{id}")
    public UserResponseDto blockUser(@PathVariable("id") Long userId) {
        return userService.blockUser(userId);
    }

    @PutMapping("/user/unblock/{id}")
    public UserResponseDto unblockUser(@PathVariable("id") Long userId) {
        return userService.unblockUser(userId);
    }

}
