package com.example.online_shop.dto.requestDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@NoArgsConstructor
public class OrderInfoRequestDto {
    private Long userId;
    private Long orderId;
    private Map<Long, Integer> products;
}
