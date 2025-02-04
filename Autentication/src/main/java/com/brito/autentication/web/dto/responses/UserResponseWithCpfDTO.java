package com.brito.autentication.web.dto.responses;

import com.brito.autentication.web.dto.responses.protocol.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseWithCpfDTO implements ResponseDTO{
    private Long id;
    private String username;
    private String cpf;
}
