package com.brito.autentication.web.dto.responses;

import com.brito.autentication.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDtoWithRoles {
    private Long id;
    private String username;
    private Set<Role> roles;
}
