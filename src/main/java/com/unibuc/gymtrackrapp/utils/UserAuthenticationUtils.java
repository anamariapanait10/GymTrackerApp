package com.unibuc.gymtrackrapp.utils;

import com.unibuc.gymtrackrapp.repositories.security.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;


public class UserAuthenticationUtils {

    public static String getLoggedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            return "redirect:/login";

        User user = (User) authentication.getPrincipal();
        return user.getUsername();
    }

}
