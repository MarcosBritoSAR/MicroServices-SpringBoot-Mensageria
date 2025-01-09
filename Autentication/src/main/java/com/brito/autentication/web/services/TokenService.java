package com.brito.autentication.web.services;

import com.auth0.jwt.JWT;
import com.brito.autentication.entities.User;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.security.core.Authentication;

@Service
public class TokenService {
    public static final String SECRET_KEY = "0123456789-0123456789-0123456789"; // TODO:MOVER PARA O DOCKER

    // TODO: Refatorar o nome desse metodo
    public String gerandoToken(User User) {

        // TODO: usar dto aqui

        Algorithm algoritimo = Algorithm.HMAC256(SECRET_KEY);
        String token = JWT.create().withIssuer("auth-api")
                .withSubject(User.getUsername())
                .withExpiresAt(buscaTempoLimiteParaExpirar())
                .sign(algoritimo);
        return token;

    }

    public Instant buscaTempoLimiteParaExpirar() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); // TODO: Retirar hard code
    }

    public String retornaUsername(String token) {

        Algorithm algorimo = Algorithm.HMAC256(SECRET_KEY);

        return JWT.require(algorimo)
                .withIssuer("auth-api")
                .build()
                .verify(token)
                .getSubject();

    }

    public boolean validarToken(String token) {

        try {
            Algorithm algorimo = Algorithm.HMAC256(SECRET_KEY);


            String subject = JWT.require(algorimo)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

/**
 * TODO: Ao usar o gateway para verificar a validade do token, o SecurityContextHolder vem direto do gateway impossibilitando que eu compare o username do token com o username do contexto
 * Solução: sem solucao
 * Problema Preciso pensar em uma forma de validar o token de forma mais solida
 */
            return subject.equals(buscarUsernameNoSecurityContextHolder());

        } catch (Exception e) {

            return false;
        }

    }

    public String buscarUsernameNoSecurityContextHolder() {

        Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();
        if (autenticacao != null && autenticacao.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) autenticacao.getPrincipal();
            return userDetails.getUsername();
        }
        return null;
    }
}
