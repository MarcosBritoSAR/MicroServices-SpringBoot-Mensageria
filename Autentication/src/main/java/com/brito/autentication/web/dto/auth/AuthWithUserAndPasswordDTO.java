package com.brito.autentication.web.dto.auth;

import com.brito.autentication.web.dto.auth.protocol.AuthDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthWithUserAndPasswordDTO implements AuthDTO{
    @NotBlank
    @Email
    @Size(min = 6)
    private String username;
    @NotBlank
    @Size(min = 6)
    private String password;
}
