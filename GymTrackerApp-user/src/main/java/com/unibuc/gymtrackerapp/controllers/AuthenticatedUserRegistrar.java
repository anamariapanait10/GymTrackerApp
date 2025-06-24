package com.unibuc.gymtrackerapp.controllers;

import com.unibuc.gymtrackerapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class AuthenticatedUserRegistrar {

    private final AuthService authService;

    @ModelAttribute
    public void registerUserIfNeeded(Authentication authentication) {
        authService.registerUser(((JwtAuthenticationToken)authentication).getTokenAttributes().get("preferred_username").toString());
    }
}