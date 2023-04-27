package com.example.online_shop.dto.responseDto;

import com.example.online_shop.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

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
    private Map<Product, Integer> cart;
}
