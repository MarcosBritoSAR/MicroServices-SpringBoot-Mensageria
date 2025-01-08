package com.brito.autentication.web.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDtoWithCpf {
    private Long id;
    private String username;
    private String cpf;
}
