package com.brito.autentication.web.controller;

import com.brito.autentication.entities.User;
import com.brito.autentication.jwt.JwtToken;
import com.brito.autentication.web.dto.AuthDto;
import com.brito.autentication.web.exception.ErrorMessage;
import com.brito.autentication.web.services.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    @PostMapping("/token")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDto dto, HttpServletRequest request) {

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
    public ResponseEntity<?> TokenIsValid(@RequestParam String token) {

        boolean isValid = tokenService.validarToken(token);

        if (isValid) {
            return ResponseEntity.ok("Token válido");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
    }
}

