package com.example.online_shop.dto.responseDto;

import com.example.online_shop.entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoResponseDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private Double balance;
}
