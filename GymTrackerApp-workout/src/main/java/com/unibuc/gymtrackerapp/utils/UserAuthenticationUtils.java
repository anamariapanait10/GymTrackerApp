package com.unibuc.gymtrackerapp.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;


public class UserAuthenticationUtils {
    @Value("${gymtrackerapp.gateway.base-url}")
    private static String gatewayBaseUrl;

    public static String getLoggedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            return "redirect:" + gatewayBaseUrl;

        return authentication.getName();
    }

}
