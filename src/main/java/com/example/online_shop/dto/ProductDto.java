package com.example.online_shop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private Long productId;
    private String name;
    private BigDecimal price;
}
