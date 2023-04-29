package com.example.online_shop.service;

import com.example.online_shop.dto.requestDto.OrderRequestDto;
import com.example.online_shop.dto.requestDto.OrderInfoRequestDto;
import com.example.online_shop.dto.requestDto.UserIdRequestDto;
import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.responseDto.OrderResponseDto;
import com.example.online_shop.dto.responseDto.UserInfoResponseDto;
import com.example.online_shop.dto.responseDto.UserResponseDto;
import com.example.online_shop.entity.User;

import java.util.List;

public interface UserService {

    UserInfoResponseDto createUser(UserRequestDto userRequestDto);

    UserInfoResponseDto createAdmin(UserRequestDto userRequestDto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long userId);

    User getUser(Long userId);

    UserInfoResponseDto deleteUser(Long userId);

    UserInfoResponseDto editUser(UserRequestDto userRequestDto);

    UserInfoResponseDto changeUserToAdmin(Long userId);

    String addBalance(UserIdRequestDto userIdRequestDto);

    UserResponseDto addOrder(OrderRequestDto orderRequestDto);

    UserResponseDto removeOrder(OrderInfoRequestDto orderRequestDto);

    List<OrderResponseDto> findAllOrdersByUserId(Long userId);

     UserResponseDto blockUser(Long userId);

     UserResponseDto unblockUser(Long userId);
}
