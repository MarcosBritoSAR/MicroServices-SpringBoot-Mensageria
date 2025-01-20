package com.brito.autentication.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.brito.autentication.entities.User;
import com.brito.autentication.web.controller.UserController;
import com.brito.autentication.web.dto.CreateUserDto;
import com.brito.autentication.web.dto.UpdateUserDto;
import com.brito.autentication.web.dto.responses.UserResponseDtoDefault;
import com.brito.autentication.web.dto.responses.UserResponseDtoWithCpf;
import com.brito.autentication.web.dto.responses.UserResponseDtoWithRoles;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final ModelMapper modelMapper;


    public Page<UserResponseDtoDefault> toPageDto(Page<User> usuarios) {
        return usuarios.map( user ->
               toDto(user).add(linkTo(methodOn(UserController.class).pegarUsersPorId(user.getId())).withSelfRel())
        );
    }

    public User toUser(CreateUserDto dto) {
        return modelMapper.map(dto, User.class);
    }

    public User toUser(UpdateUserDto dto) { return modelMapper.map(dto, User.class); }


    //UR Default

    public UserResponseDtoDefault toDto(User usuario) {
        return modelMapper.map(usuario, UserResponseDtoDefault.class);
    }

    //UR Roles
    public UserResponseDtoWithRoles toDtoWithRoles(User usuario) {
        return modelMapper.map(usuario, UserResponseDtoWithRoles.class);
    }

    //UR CPF
    public UserResponseDtoWithCpf toDtoWithCpf(User usuario) {
        return modelMapper.map(usuario, UserResponseDtoWithCpf.class);
    }

}
