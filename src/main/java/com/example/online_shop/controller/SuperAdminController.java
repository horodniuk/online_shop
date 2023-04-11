package com.example.online_shop.controller;

import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.responseDto.UserResponseDto;
import com.example.online_shop.entity.User;
import com.example.online_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/superadmin/admin")
@RequiredArgsConstructor
public class SuperAdminController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping
    public UserResponseDto addAdmin(UserRequestDto userRequestDto) {
        return userService.addUser(userRequestDto);
    }

    @DeleteMapping("/{id}")
    public UserResponseDto deleteAdmin(@PathVariable("id") Long userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping
    public UserResponseDto editAdmin(@RequestBody User user) {
        UserRequestDto userRequestDto = modelMapper.map(user, UserRequestDto.class);
        return userService.editUser(user.getUserId(), userRequestDto);
    }
}
