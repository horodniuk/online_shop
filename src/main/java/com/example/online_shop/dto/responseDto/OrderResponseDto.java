package com.example.online_shop.dto.responseDto;

import com.example.online_shop.dto.requestDto.ProductRequestDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private LocalDateTime orderDate;
    private Double totalPrice;
    private Map<ProductRequestDto, Integer> products;
}
