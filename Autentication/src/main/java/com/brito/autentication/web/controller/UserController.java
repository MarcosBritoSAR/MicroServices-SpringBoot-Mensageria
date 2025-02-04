package com.brito.autentication.web.controller;

import com.brito.autentication.entities.User;
import com.brito.autentication.web.dto.auth.AuthWithUserAndPasswordDTO;
import com.brito.autentication.web.dto.created.protocol.CreateDTO;
import com.brito.autentication.web.dto.responses.UserResponseDefaultDTO;
import com.brito.autentication.web.dto.responses.protocol.ResponseDTO;
import com.brito.autentication.web.dto.mapper.UserMapper;
import com.brito.autentication.web.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RequestMapping("/api/v2")
@RestController
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PagedResourcesAssembler<ResponseDTO> assembler;

    @PostMapping
    public ResponseEntity<ResponseDTO> createUser(@Valid @RequestBody CreateDTO dto) {

        User user = userService.salvar(userMapper.toUser(dto));
        var dtoUser = (UserResponseDefaultDTO) userMapper.toDto(user, UserResponseDefaultDTO.class);
        dtoUser.add(linkTo(methodOn(UserController.class).createUser(dto)).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoUser);
        
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable Long id) {
        User user = userService.buscarUserPorId(id);
        var dtoUser = (UserResponseDefaultDTO) userMapper.toDto(user, UserResponseDefaultDTO.class);
        dtoUser.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(dtoUser);
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedModel<EntityModel<ResponseDTO>>> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "12") Integer limit,
            @RequestParam(value = "sort", defaultValue = "asc") String sort
    ) {

        var direction = sort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Page<User> users = userService.buscarUsers(page, limit, Sort.by(direction, "username"));

        Page<ResponseDTO> response = userMapper.toPageDto(users);

        Link link = linkTo(methodOn(UserController.class).getAll(page, limit, sort)).withSelfRel();

        return ResponseEntity.status(HttpStatus.OK).body(assembler.toModel(response, link));

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR') AND (#id == authentication.principal.id)")
    public ResponseEntity<ResponseDTO> updateById(@PathVariable Long id,
                                                                       @Valid @RequestBody AuthWithUserAndPasswordDTO dto) {
        User userAtualizado = userService.atualizUser(id, userMapper.toUser(dto));
        var dtoUser = (UserResponseDefaultDTO) userMapper.toDto(userAtualizado, UserResponseDefaultDTO.class);
        dtoUser.add(linkTo(methodOn(UserController.class).updateById(id, dto)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(dtoUser);
    }


}
