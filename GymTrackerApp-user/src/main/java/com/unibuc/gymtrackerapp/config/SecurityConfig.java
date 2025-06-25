package com.unibuc.gymtrackerapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
        jwtConverter.setPrincipalClaimName("preferred_username");
        return jwtConverter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(
                                "/debug/**",
                                "/actuator/**"
                        ).permitAll()
                        .requestMatchers("/access_denied").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        ).authenticationEntryPoint(((request, response, authException) -> {
                            if (authException instanceof InvalidBearerTokenException){
                                String redirectUri = "http://localhost:8080/realms/gymtrackerapp/protocol/openid-connect/auth" +
                                    "?client_id=gymtrackerapp" +
                                    "&response_type=code" +
                                    "&scope=openid" +
                                        "&redirect_uri=http://localhost:8071/gymtrackerapp/workout/sessions";
                                response.sendRedirect(redirectUri);
                            }
                        }))
                );
        return http.build();
    }
}
