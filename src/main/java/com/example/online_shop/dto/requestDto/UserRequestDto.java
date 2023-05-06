package com.example.online_shop.dto.requestDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestDto {
    private Long userId;
    @NotBlank(message = "First name of user is required field.")
    private String firstName;
    @NotBlank(message = "Last name of user is required field.")
    private String lastName;
    @Pattern(regexp = "\\w+@\\w+\\.[a-z]{2,3}", message = "please use pattern hello@gmail.com")
    private String email;
    @NotBlank(message = "Password is required field")
    private String password;
    @Min(value = 0, message = "Balance must be bigger than -1.")
    private Double balance;
}
