package com.example.online_shop.dto.responseDto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class OrderResponseDto {
    private Long orderId;
    private LocalDateTime orderDate;
    private Long userId;
    private Map<Long, Integer> products;
}
