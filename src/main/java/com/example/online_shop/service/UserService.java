package com.example.online_shop.service;

import com.example.online_shop.dto.requestDto.OrderRequestDto;
import com.example.online_shop.dto.requestDto.OrderInfoRequestDto;
import com.example.online_shop.dto.requestDto.UserIdRequestDto;
import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.responseDto.OrderResponseDto;
import com.example.online_shop.dto.responseDto.UserResponseDto;
import com.example.online_shop.entity.User;

import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto createAdmin(UserRequestDto userRequestDto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long userId);

    User getUser(Long userId);

    UserResponseDto deleteUser(Long userId);

    UserResponseDto editUser(UserRequestDto userRequestDto);

    UserResponseDto changeUserToAdmin(Long userId);

    String addBalance(UserIdRequestDto userIdRequestDto);

    UserResponseDto addOrder(OrderRequestDto orderRequestDto);

    UserResponseDto removeOrder(OrderInfoRequestDto orderRequestDto);

    List<OrderResponseDto> findAllOrdersByUserId(Long userId);
}
