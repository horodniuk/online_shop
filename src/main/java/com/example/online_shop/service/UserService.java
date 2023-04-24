package com.example.online_shop.service;

import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.requestDto.UserRequestToChangeDto;
import com.example.online_shop.dto.responseDto.OrderResponseDto;
import com.example.online_shop.dto.responseDto.UserResponseDto;
import com.example.online_shop.entity.Product;
import com.example.online_shop.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto createAdmin(UserRequestDto userRequestDto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long userId);

    User getUser(Long userId);

    UserResponseDto deleteUser(Long userId);

    UserResponseDto editUser(UserRequestDto userRequestDto);

    UserResponseDto changeUserToAdmin(UserRequestToChangeDto userRequestDto);

    UserResponseDto addOrderFromCart(Long userId);

    UserResponseDto removeOrderFromUser(Long userId, Long orderId);

    UserResponseDto addProductToCart(Long productId, int quantity, Long userId);

    OrderResponseDto removeProductFromCart(Long productId, int quantity, Long userId);

    UserResponseDto clearCart(Long userId);

    Map<Product, Integer> showAllProductsInCart(Long userId);

    List<OrderResponseDto> getAllUserOrders(Long userId);

    String addBalance(UserRequestDto userRequestDto);

}
