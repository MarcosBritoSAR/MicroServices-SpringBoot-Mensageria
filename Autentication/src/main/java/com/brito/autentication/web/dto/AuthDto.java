package com.brito.autentication.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthDto {
    @NotBlank
    @Email
    @Size(min = 6)
    private String username;
    @NotBlank
    @Size(min = 6)
    private String password;
}
