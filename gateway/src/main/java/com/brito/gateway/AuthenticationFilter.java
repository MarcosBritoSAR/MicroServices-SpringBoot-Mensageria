package com.brito.gateway;

import com.brito.gateway.infra.ReactiveValidToken;
import feign.FeignException;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;


@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {


    @Autowired
    @Lazy
    private ReactiveValidToken reactiveValidToken;


    private final Map<String, List<HttpMethod>> publicPaths = Map.of(
            "/users", List.of(HttpMethod.POST),
            "/auth/token", List.of(HttpMethod.POST)
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getPath().toString();
        HttpMethod method = exchange.getRequest().getMethod();

        if (isPublicRoute(path, method)) {
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (token == null || token.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String cleanToken = token.replace("Bearer ", "");

        return reactiveValidToken.tokenIsValid(cleanToken)
                .then(chain.filter(exchange))
                .onErrorResume(ex -> {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }

    private boolean isPublicRoute(String path, HttpMethod method) {
        int index = path.indexOf("/", 1); //Quero que ele procure o primeiro / que encontrar
        path = path.substring(index); //quero pegar tudo a partir de /

        List<HttpMethod> result = publicPaths.get(path);
        return result != null && result.contains(method);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
