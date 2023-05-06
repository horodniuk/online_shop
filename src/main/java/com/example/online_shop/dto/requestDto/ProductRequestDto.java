package com.example.online_shop.dto.requestDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequestDto {
    private Long productId;

    @NotBlank(message = "Name of product is required field")
    private String name;

    @Min(value = 1, message = "must be greater than 0")
    private Double price;
}
