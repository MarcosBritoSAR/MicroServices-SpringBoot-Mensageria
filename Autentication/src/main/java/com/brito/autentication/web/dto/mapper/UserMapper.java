package com.brito.autentication.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.brito.autentication.entities.User;
import com.brito.autentication.web.dto.CreateUserDto;
import com.brito.autentication.web.dto.responses.UserResponseDtoDefault;
import com.brito.autentication.web.dto.responses.UserResponseDtoWithRoles;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final ModelMapper modelMapper;


    public List<UserResponseDtoDefault> toListDto(List<User> usuarios) {
        return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }

    public User toUser(CreateUserDto dto) {
        return modelMapper.map(dto, User.class);
    }


    //UR Default

    public UserResponseDtoDefault toDto(User usuario) {
        return modelMapper.map(usuario, UserResponseDtoDefault.class);
    }

    //UR Roles
    public UserResponseDtoWithRoles toDtoWithRoles(User usuario) {
        return modelMapper.map(usuario, UserResponseDtoWithRoles.class);
    }

    //UR CPF
    public UserResponseDtoDefault toDtoWithCpf(User usuario) {
        return modelMapper.map(usuario, UserResponseDtoDefault.class);
    }

}
