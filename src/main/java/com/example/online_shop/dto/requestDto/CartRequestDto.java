package com.example.online_shop.dto.requestDto;



import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartRequestDto {
    private Long userId;
    private Long productId;
    private int quantity;
}
