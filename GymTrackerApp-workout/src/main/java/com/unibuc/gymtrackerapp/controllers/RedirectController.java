package com.unibuc.gymtrackerapp.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class RedirectController {
    @Value("${gymtrackerapp.gateway.base-url}")
    private String gatewayBaseUrl;

    @GetMapping("/")
    public String redirectToLogin(Authentication authentication) {
        if (authentication.isAuthenticated())
            return "redirect:" + gatewayBaseUrl + "/workout/sessions";

        String redirectUri = "http://localhost:8080/realms/gymtrackerapp/protocol/openid-connect/auth" +
                "?client_id=gymtrackerapp" +
                "&response_type=code" +
                "&scope=openid" +
                "&redirect_uri=http://localhost:8071/gymtrackerapp/workout/sessions";

        return "redirect:" + redirectUri;
    }

    @GetMapping("/access_denied")
    public String accessDeniedPage() {
        return "access-denied";
    }

    @GetMapping("/debug/auth")
    public ResponseEntity<String> debug(Authentication authentication) {
        return ResponseEntity.ok("Authorities: " + authentication.getAuthorities());
    }

    @GetMapping("/debug/token")
    public ResponseEntity<String> debugToken(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        return ResponseEntity.ok("Authorization: " + authHeader);
    }
}