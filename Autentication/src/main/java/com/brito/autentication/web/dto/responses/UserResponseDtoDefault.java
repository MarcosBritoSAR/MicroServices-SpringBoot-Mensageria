package com.brito.autentication.web.dto.responses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserResponseDtoDefault {

    private Long id;
    private String username;
}
