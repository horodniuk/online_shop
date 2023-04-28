package com.example.online_shop.dto.requestDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;


@Data
@NoArgsConstructor
public class OrderRequestDto {
    private Long userId;
    private Map<Long, Integer> products;
}
