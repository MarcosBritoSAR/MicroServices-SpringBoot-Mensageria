package com.brito.gateway.infra;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "autentication", path ="/auth/validar-token", qualifier = "authFeignClient")
public interface ReactiveValidToken {

//Configurado pra não receber corpo e está recebendo um Corpo Token
    @GetMapping
    Mono<Void> tokenIsValid(@RequestParam("token") String token);
}
