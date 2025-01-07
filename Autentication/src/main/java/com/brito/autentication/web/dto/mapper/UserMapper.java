package com.brito.autentication.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.brito.autentication.entities.User;
import com.brito.autentication.web.dto.CreateUserDto;
import com.brito.autentication.web.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final ModelMapper modelMapper; // Fixed by ensuring ModelMapper is properly registered as a Spring bean in the context.

    public User toUser(CreateUserDto dto) {
        return modelMapper.map(dto, User.class);
    }

    public UserResponseDto toDto(User usuario) {
        return modelMapper.map(usuario, UserResponseDto.class);
    }

    public List<UserResponseDto> toListDto(List<User> usuarios) {
        return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }

}
