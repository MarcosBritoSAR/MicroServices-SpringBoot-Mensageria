package com.brito.autentication.web.dto.mapper;

import com.brito.autentication.entities.User;
import com.brito.autentication.web.controller.UserController;
import com.brito.autentication.web.dto.auth.protocol.AtuthDTO;
import com.brito.autentication.web.dto.created.protocol.CreateDTO;
import com.brito.autentication.web.dto.protocol.DTO;
import com.brito.autentication.web.dto.responses.UserResponseDefaultDTO;
import com.brito.autentication.web.dto.responses.protocol.ResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@Component
public class UserMapper<T> {

    private final ModelMapper modelMapper;

    public Page<ResponseDTO> toPageDto(Page<User> usuarios) {

        return usuarios.map(user -> ((UserResponseDefaultDTO) toDto(user, UserResponseDefaultDTO.class))
                .add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel()));

    }

    public User toUser(CreateDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    public User toUser(AtuthDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    public <T extends DTO> T toDto(User user, Class<T> destinationType) {
        return modelMapper.map(user, destinationType);
    }

    

}
