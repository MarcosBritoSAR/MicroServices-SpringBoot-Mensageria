package com.brito.autentication.web.controller;

import com.brito.autentication.entities.User;
import com.brito.autentication.jwt.JwtToken;
import com.brito.autentication.rabbitmq.QueueSender;
import com.brito.autentication.web.dto.AuthDto;
import com.brito.autentication.web.dto.mapper.UserMapper;
import com.brito.autentication.web.dto.responses.UserResponseDtoWithRoles;
import com.brito.autentication.web.exception.ErrorMessage;
import com.brito.autentication.web.services.TokenService;
import com.brito.autentication.web.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpIllegalStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final QueueSender sender;


    @PostMapping("/token")
    public ResponseEntity<?> login(@RequestBody AuthDto dto, HttpServletRequest request) {

        try {

            var username = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            var auth = this.authenticationManager.authenticate(username);
            JwtToken token = new JwtToken(tokenService.gerandoToken((User) auth.getPrincipal()));

            String message = "Testando o producer";


            if (sender.send(message)) {

                return ResponseEntity.ok().body(token);

            } else {

                return ResponseEntity
                        .status(HttpStatus.MULTI_STATUS)
                        .body(token);
            }

        } catch (AuthenticationException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Invalid Crendentials"));
        } catch (Exception ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
        }

    }

    @GetMapping("/validar-token")
    public Mono<ResponseEntity<Void>> tokenIsValid(@RequestParam String token) {

        return Mono.fromSupplier(() -> {
            boolean isValid = tokenService.validarToken(token);

            if (isValid) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        });
    }


    @GetMapping("/raiseTheLevel/{id}")
    public ResponseEntity<UserResponseDtoWithRoles> raiseTheLevelUser(@PathVariable Long id) {

        User user = userService.raiseTheLevel(id);

        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDtoWithRoles(user));

    }


}

