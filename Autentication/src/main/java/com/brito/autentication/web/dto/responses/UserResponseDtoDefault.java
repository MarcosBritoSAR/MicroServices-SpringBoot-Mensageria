package com.brito.autentication.web.dto.responses;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.Mapping;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserResponseDtoDefault extends RepresentationModel<UserResponseDtoDefault> {

    private Long id;
    private String username;

}
