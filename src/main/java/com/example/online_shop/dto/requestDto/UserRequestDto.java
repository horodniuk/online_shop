package com.example.online_shop.dto.requestDto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private Double balance;
}
