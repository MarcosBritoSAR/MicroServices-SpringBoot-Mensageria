package com.brito.autentication.web.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.hateoas.RepresentationModel;
import com.brito.autentication.web.dto.responses.protocol.ResponseDTO;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDefaultDTO extends RepresentationModel<UserResponseDefaultDTO> implements ResponseDTO{

    private Long id;
    private String username;

}
