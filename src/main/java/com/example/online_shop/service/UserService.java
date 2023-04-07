package com.example.online_shop.service;

import com.example.online_shop.dto.requestDto.OrderRequestDto;
import com.example.online_shop.dto.requestDto.ProductRequestDto;
import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.responseDto.UserResponseDto;
import com.example.online_shop.entity.User;

import java.util.List;

public interface UserService {
    UserResponseDto addUser(UserRequestDto userRequestDto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long userId);

    UserResponseDto deleteUser(Long userId);

    UserResponseDto editUser(Long userId, UserRequestDto userRequestDto);

    UserResponseDto addOrderFromCart(OrderRequestDto orderRequestDto,);

    UserResponseDto addProductToCart(ProductRequestDto productRequestDto, int quantity);
}
