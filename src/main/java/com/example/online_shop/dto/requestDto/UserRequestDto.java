package com.example.online_shop.dto.requestDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private Double balance;


}
