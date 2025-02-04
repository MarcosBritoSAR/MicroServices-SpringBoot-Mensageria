package com.brito.autentication.web.controller;

import com.brito.autentication.jwt.JwtToken;
import com.brito.autentication.web.dto.auth.AuthWithUserAndPasswordDTO;
import com.brito.autentication.web.dto.responses.protocol.ResponseDTO;
import com.brito.autentication.web.services.TokenService;
import com.brito.autentication.web.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/auth")
public class AuthController {

    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody AuthWithUserAndPasswordDTO dto, HttpServletRequest request) {

        JwtToken token = tokenService.generateToken(dto);
        return ResponseEntity.ok().body(token);

    }

    @GetMapping("/token-isvalid")
    public Mono<ResponseEntity<Void>> tokenIsValid(@RequestParam String token) {

        return Mono.fromSupplier(() -> {

            boolean isValid = tokenService.validarToken(token);

            if (isValid) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        });
    }

    // TODO: Alter this design

    @GetMapping("/raiseTheLevel/{id}")
    public ResponseEntity<ResponseDTO> raiseTheLevelUser(@PathVariable Long id) {

        ResponseDTO response = userService.raiseTheLevel(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);

    }

}
