package com.brito.autentication.web.dto.responses;

import com.brito.autentication.entities.Role;
import com.brito.autentication.web.dto.responses.protocol.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseWithRolesDTO implements ResponseDTO{
    private Long id;
    private String username;
    private Set<Role> roles;
}
