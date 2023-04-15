package com.example.online_shop.controller;

import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.responseDto.UserResponseDto;
import com.example.online_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/superadmin/admin")
@RequiredArgsConstructor
public class SuperAdminController {
    private final UserService userService;

    @PostMapping
    public UserResponseDto addAdmin(UserRequestDto userRequestDto) {
        return userService.addAdmin(userRequestDto);
    }

    @DeleteMapping("/{id}")
    public UserResponseDto deleteAdmin(@PathVariable("id") Long userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping
    public UserResponseDto assignAdmin(@RequestBody UserRequestDto userRequestDto) {
        return userService.changeUserToAdmin(userRequestDto);
    }
}
