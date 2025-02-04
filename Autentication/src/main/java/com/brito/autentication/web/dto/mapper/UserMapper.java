package com.brito.autentication.web.dto.mapper;

import com.brito.autentication.entities.User;
import com.brito.autentication.web.controller.UserController;
import com.brito.autentication.web.dto.auth.AuthWithUserAndPasswordDTO;
import com.brito.autentication.web.dto.auth.protocol.AtuthDTO;
import com.brito.autentication.web.dto.created.CreateUserDefaultDTO;
import com.brito.autentication.web.dto.created.protocol.CreateDTO;
import com.brito.autentication.web.dto.protocol.DTO;
import com.brito.autentication.web.dto.responses.UserResponseDefaultDTO;
import com.brito.autentication.web.dto.responses.UserResponseWithCpfDTO;
import com.brito.autentication.web.dto.responses.UserResponseWithRolesDTO;
import com.brito.autentication.web.dto.responses.protocol.ResponseDTO;
import com.rabbitmq.client.RpcClient.Response;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.support.converter.ClassMapper;
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


        return usuarios.map(user -> toDto(user, UserResponseDefaultDTO.class)).;

        // return usuarios.map(user -> toDto(user, UserResponseDefaultDTO.class)).toList()
        //         .add(linkTo(methodOn(UserController.class).getUserById(user.getId()).withSelfRel()));


    }

    public <T extends DTO> T ToEntityType(T dto, Class<T> destinationType) {
        return modelMapper.map(dto, destinationType);
    }

    public <T extends ResponseDTO> ResponseDTO toDto(User usuario, Class<T> destinationType) {
        return modelMapper.map(usuario, destinationType);
    }

}
