package com.example.online_shop.dto.requestDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
public class OrderRequestDto {
    private Long orderId;
    private LocalDateTime orderDate;
    private Long userId;
}
