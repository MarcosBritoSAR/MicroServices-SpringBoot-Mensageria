package com.brito.autentication.web.services;

import com.auth0.jwt.JWT;
import com.brito.autentication.entities.User;
import com.brito.autentication.exceptions.AutenticacaoException;
import com.brito.autentication.exceptions.SendEmailException;
import com.brito.autentication.jwt.JwtToken;
import com.brito.autentication.rabbitmq.QueueSender;
import com.brito.autentication.web.dto.auth.protocol.AuthDTO;
import lombok.RequiredArgsConstructor;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
@Service
public class TokenService {

    @Value("${spring.secret_key}")
    private String secret_key;
    private final AuthenticationManager authenticationManager;
    private final QueueSender sender;

    public JwtToken generateToken(AuthDTO dto) {

        try {

            var username = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            var auth = authenticationManager.authenticate(username);

            if (auth == null) {
                throw new AutenticacaoException("invalid credential");
            }

            Algorithm algoritimo = Algorithm.HMAC256(secret_key);
            String token = JWT.create().withIssuer("auth-api")
                    .withSubject(((User) auth.getPrincipal()).getUsername())
                    .withExpiresAt(buscaTempoLimiteParaExpirar())
                    .sign(algoritimo);

            String message = "testing";
            if (!sender.send(message)) {
                throw new SendEmailException();
            }

            return new JwtToken(token);

        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Instant buscaTempoLimiteParaExpirar() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); // TODO: Retirar hard code
    }

    public String retornaUsername(String token) {

        Algorithm algorimo = Algorithm.HMAC256(secret_key);

        return JWT.require(algorimo)
                .withIssuer("auth-api")
                .build()
                .verify(token)
                .getSubject();

    }

    public boolean validarToken(String token) {

        try {
            Algorithm algorimo = Algorithm.HMAC256(secret_key);

            String subject = JWT.require(algorimo)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

            return subject != null;

        } catch (Exception e) {

            return false;

        }

    }

    public String getUsernameInSecurityContextHolder() {

        Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();
        if (autenticacao != null && autenticacao.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) autenticacao.getPrincipal();
            return userDetails.getUsername();
        }
        return null;
    }
}
