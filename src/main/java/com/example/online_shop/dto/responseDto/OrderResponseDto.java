package com.example.online_shop.dto.responseDto;

import com.example.online_shop.entity.Product;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class OrderResponseDto {
    private Long orderId;
    private LocalDateTime orderDate;
    private Long userId;
    private Map<Product, Integer> products;
}
