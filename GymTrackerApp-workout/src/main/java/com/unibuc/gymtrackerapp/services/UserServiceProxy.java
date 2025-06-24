package com.unibuc.gymtrackerapp.services;

import com.unibuc.gymtrackerapp.domain.entity.security.User;

import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserServiceProxy {
    private final WebClient webClient;

    public UserServiceProxy(WebClient.Builder builder) {
        this.webClient = builder.filter(new ServletBearerExchangeFilterFunction()).build(); // uses load-balanced builder
    }

    public Mono<User> getUserByEmail(String username) {
        return webClient
                .get()
                .uri("lb://GYMTRACKERUSER/users/" + username) // Eureka service name
                .header(HttpHeaders.AUTHORIZATION)
                .retrieve()
                .bodyToMono(User.class);
    }
}
