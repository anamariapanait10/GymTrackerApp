package com.unibuc.gymtrackerapp.controllers;

import com.unibuc.gymtrackerapp.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        String keycloakLogoutUrl = "http://localhost:8080/realms/gymtrackerapp/protocol/openid-connect/logout?redirect_uri" +
                "=http://localhost:8071";
        authService.logoutUser(request);
        return "redirect:" + keycloakLogoutUrl;
    }
}
