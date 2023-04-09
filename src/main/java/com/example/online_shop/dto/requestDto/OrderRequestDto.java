package com.example.online_shop.dto.requestDto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class OrderRequestDto {
    private LocalDateTime orderDate;
    private Long userId;
    private Map<Long, Integer> products;

}
