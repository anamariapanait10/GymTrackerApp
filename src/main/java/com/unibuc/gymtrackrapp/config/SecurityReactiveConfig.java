package com.unibuc.gymtrackrapp.config;

import com.unibuc.gymtrackrapp.services.security.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@Profile("mongodb")
public class SecurityReactiveConfig {

    private final UserDetailsService userDetailsService;

    public SecurityReactiveConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                        .pathMatchers("/product/form").hasRole("ADMIN")
                        .pathMatchers("/api/**", "/workouts/**").hasAnyRole("ADMIN", "GUEST")
                        .anyExchange().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .authenticationSuccessHandler((webFilterExchange, authentication) ->
                                webFilterExchange.getExchange().getResponse().setComplete())
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler((exchange, denied) -> exchange.getExchange().getResponse().setComplete())
                )
                .userDetailsService(userDetailsService)
                .build();
    }
}