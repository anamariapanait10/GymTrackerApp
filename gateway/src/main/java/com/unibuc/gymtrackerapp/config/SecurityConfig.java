package com.unibuc.gymtrackerapp.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public ReactiveOAuth2AuthorizedClientManager authorizedClientManager(
            ReactiveClientRegistrationRepository clientRegistrationRepository,
            ReactiveOAuth2AuthorizedClientService authorizedClientService) {

        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager manager =
                new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientService);

        ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider = ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
                .authorizationCode()
                .refreshToken()
                .build();

        manager.setAuthorizedClientProvider(authorizedClientProvider);

        return manager;
    }

    @Bean
    public GlobalFilter tokenRelayFilter(ReactiveOAuth2AuthorizedClientManager authorizedClientManager) {
        return (exchange, chain) -> exchange.getPrincipal()
                .cast(OAuth2AuthenticationToken.class)
                .flatMap(authentication -> {
                    OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                            .withClientRegistrationId(authentication.getAuthorizedClientRegistrationId())
                            .principal(authentication)
                            .build();

                    return authorizedClientManager
                            .authorize(authorizeRequest)
                            .flatMap(authorizedClient -> {
                                String token = authorizedClient.getAccessToken().getTokenValue();
                                System.out.println("Access token: " + token.substring(0, 10) + "...");
                                System.out.println("Expires at: " + authorizedClient.getAccessToken().getExpiresAt());
                                ServerHttpRequest mutatedRequest = exchange.getRequest()
                                        .mutate()
                                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                        .build();
                                return chain.filter(exchange.mutate().request(mutatedRequest).build());
                            });
                })
                .switchIfEmpty(chain.filter(exchange));
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(auth -> auth.anyExchange().authenticated())
                .oauth2Login(withDefaults())
                .oauth2Client(withDefaults());
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }

}