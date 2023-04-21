package com.example.online_shop.dto.responseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private Double balance;
    private List<OrderResponseDto> orders;
    private Long cartId;
}
