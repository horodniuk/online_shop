package com.example.online_shop.dto.requestDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserIdRequestDto {
    private Long userId;
    @Min(value = 0, message = "Balance must be bigger than -1.")
    private Double balance;
}
