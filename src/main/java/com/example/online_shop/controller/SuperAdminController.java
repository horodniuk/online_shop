package com.example.online_shop.controller;

import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.responseDto.UserInfoResponseDto;
import com.example.online_shop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/superadmin/admin")
@RequiredArgsConstructor
public class SuperAdminController {
    private final UserService userService;

    @PostMapping
    public UserInfoResponseDto addAdmin(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.createAdmin(userRequestDto);
    }

    @DeleteMapping("/{id}")
    public UserInfoResponseDto deleteAdmin(@PathVariable("id") Long userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping("/{id}")
    public UserInfoResponseDto assignAdmin(@PathVariable("id") Long userId) {
        return userService.changeUserToAdmin(userId);
    }
}
