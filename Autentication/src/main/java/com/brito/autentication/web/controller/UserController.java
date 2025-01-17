package com.brito.autentication.web.controller;

import com.brito.autentication.entities.User;
import com.brito.autentication.web.dto.CreateUserDto;
import com.brito.autentication.web.dto.UpdateUserDto;
import com.brito.autentication.web.dto.responses.UserResponseDtoDefault;
import com.brito.autentication.web.dto.mapper.UserMapper;
import com.brito.autentication.web.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class  UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponseDtoDefault> criarUser(@Valid @RequestBody CreateUserDto dto) {
        User user = userService.salvar(userMapper.toUser(dto));
        var dtoUser = userMapper.toDto(user);
        dtoUser.add(linkTo(methodOn(UserController.class).criarUser(dto)).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoUser);
    }
    
    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDtoDefault> pegarUsersPorId(@PathVariable Long id) {
        User user = userService.buscarUserPorId(id);
        var dtoUser = userMapper.toDto(user);
        dtoUser.add(linkTo(methodOn(UserController.class).pegarUsersPorId(id)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(dtoUser);
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponseDtoDefault>> pegarTodosUsers(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "12") Integer limit,
            @RequestParam(value = "sort", defaultValue = "asc") String sort
    ) {

         var direction = sort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Page<User> users = userService.buscarUsers(page, limit, Sort.by(direction, "username"));

        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toPageDto(users));

    }
    
    @PutMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN','OPERATOR') AND (#id == authentication.principal.id)")
    public ResponseEntity<UserResponseDtoDefault> atualizarPessoaPorId(@PathVariable Long id,
                                                                       @Valid @RequestBody UpdateUserDto dto) {
        User userAtualizado = userService.atualizUser(id, userMapper.toUser(dto));
        var dtoUser = userMapper.toDto(userAtualizado);
        dtoUser.add(linkTo(methodOn(UserController.class).atualizarPessoaPorId(id,dto)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(dtoUser);
    }


}
