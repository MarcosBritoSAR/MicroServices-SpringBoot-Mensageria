package com.brito.autentication.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class CreateUserDto {

    @NotBlank
    @Size(min = 5)
    @Email
    private String username;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotBlank
    @CPF
    private String cpf;

    public CreateUserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
