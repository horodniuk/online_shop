package com.example.online_shop.dto.responseDto;

import com.example.online_shop.entity.Order;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private Double balance;
    private List<Long> orders;
    private Long cartId;
}
