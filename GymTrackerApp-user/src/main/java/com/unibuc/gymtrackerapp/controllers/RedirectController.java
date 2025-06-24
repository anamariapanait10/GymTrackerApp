package com.unibuc.gymtrackerapp.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Objects;

@Controller
public class RedirectController {
    @Value("${gymtrackerapp.gateway.base-url}")
    private String gatewayBaseUrl;

    @GetMapping("/")
    public String redirectToLogin(Authentication authentication) {
        if (Objects.isNull(authentication))
            return "redirect:" + gatewayBaseUrl + "/user/access_denied";
//        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
//            return "redirect:" + gatewayBaseUrl + "/user/admin/users";
//        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
//            return "redirect:" + gatewayBaseUrl + "/chat/chat";
//        }
        return "redirect:" + gatewayBaseUrl + "/user/access_denied";
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