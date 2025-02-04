package com.brito.autentication.web.controller;

import com.brito.autentication.entities.User;
import com.brito.autentication.web.dto.created.protocol.CreateDTO;
import com.brito.autentication.web.dto.responses.protocol.ResponseDTO;
import com.brito.autentication.web.services.UserService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v2")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponseDTO> createUser(@Valid @RequestBody CreateDTO dto) {

        var dtoUser = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoUser);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable Long id) {

        var response = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedModel<EntityModel<ResponseDTO>>> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "12") Integer limit,
            @RequestParam(value = "sort", defaultValue = "asc") String sort) {

        var response = userService.getAll(page, limit, sort);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR') AND (#id == authentication.principal.id)")
    public ResponseEntity<ResponseDTO> updateById(@PathVariable Long id,
            @Valid @RequestBody User updateUser) {

        var response = userService.uptadeById(id, updateUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
    
}
