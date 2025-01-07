package com.brito.autentication.web.controller;

import java.util.List;

import com.brito.autentication.entities.User;
import com.brito.autentication.web.dto.CreateUserDto;
import com.brito.autentication.web.dto.UserResponseDto;
import com.brito.autentication.web.dto.mapper.UserMapper;
import com.brito.autentication.web.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponseDto> criarUser(@Valid @RequestBody CreateUserDto dto) {
        User user = userService.salvar(userMapper.toUser(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(user));
    }
    
    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> pegarUsersPorId(@PathVariable Long id) {
        User user = userService.buscarUserPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDto(user));
    }


    //TODO: Remover isso aqui
    @GetMapping
  //  @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> pegarTodosUsers() {
        List<User> users = userService.buscarUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toListDto(users));
    }
    
    @PutMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN','OPERATOR') AND (#id == authentication.principal.id)")
    public ResponseEntity<UserResponseDto> AtualizarPessoaPorId(@PathVariable Long id,
            @Valid @RequestBody CreateUserDto dto) {
        User userAtualizado = userService.atualizUser(id, userMapper.toUser(dto));
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDto(userAtualizado));
    }

}
