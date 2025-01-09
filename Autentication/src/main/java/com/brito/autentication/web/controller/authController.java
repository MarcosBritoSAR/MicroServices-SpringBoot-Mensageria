package com.brito.autentication.web.controller;

import com.brito.autentication.entities.User;
import com.brito.autentication.jwt.JwtToken;
import com.brito.autentication.web.dto.AuthDto;
import com.brito.autentication.web.dto.responses.UserResponseDtoDefault;
import com.brito.autentication.web.dto.mapper.UserMapper;
import com.brito.autentication.web.dto.responses.UserResponseDtoWithRoles;
import com.brito.autentication.web.exception.ErrorMessage;
import com.brito.autentication.web.services.TokenService;
import com.brito.autentication.web.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;
    private final UserMapper userMapper;


    @PostMapping("/token")
    public ResponseEntity<?> login(@RequestBody AuthDto dto, HttpServletRequest request) {

        try {

            var username = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            var auth = this.authenticationManager.authenticate(username);
            JwtToken token = new JwtToken(tokenService.gerandoToken((User) auth.getPrincipal()));

            return ResponseEntity.ok().body(token);


        } catch (AuthenticationException e) {

            return ResponseEntity
                    .badRequest()
                    .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Invalid Crendentials"));

        }

    }

    @GetMapping("/validar-token")
    public ResponseEntity<?> tokenIsValid(@RequestParam String token) {

        boolean isValid = tokenService.validarToken(token);

        if (isValid) {
            return ResponseEntity.ok("Token válido");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
    }


    @GetMapping("/raiseTheLevel/{id}")
    public ResponseEntity<UserResponseDtoWithRoles> raiseTheLevelUser(@PathVariable Long id){

        User user = userService.raiseTheLevel(id);

        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDtoWithRoles(user));

    }




}

