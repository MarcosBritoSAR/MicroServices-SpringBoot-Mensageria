package com.brito.autentication.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDto {

    @NotBlank
    @Size(min = 5)
    @Email
    private String usuario;
    @NotBlank
    @Size(min = 6)
    private String senha; 

    public CreateUserDto(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

}
