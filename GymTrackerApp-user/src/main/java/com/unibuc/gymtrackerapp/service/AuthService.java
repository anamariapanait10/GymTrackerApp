package com.unibuc.gymtrackerapp.service;

import com.unibuc.gymtrackerapp.dtos.UserCredentialsDto;
import com.unibuc.gymtrackerapp.domain.security.Authority;
import com.unibuc.gymtrackerapp.domain.security.User;
import com.unibuc.gymtrackerapp.repositories.AuthorityRepository;
import com.unibuc.gymtrackerapp.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
    private UserRepository userRepository;
    private AuthorityRepository roleRepository;

    @Transactional
    public void registerUser(String username) {
        if(userRepository.findByUsername(username).isPresent()) {
            return; // User already exists, no need to proceed
        }

        Authority role = this.roleRepository.findByRole("ROLE_USER");

        this.userRepository.save(
                User.builder()
                        .username(username)
                        .authority(role)
                        .build());
        log.info("Registered user with username: {}", username);
    }

    public void logoutUser(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info("Logging out user with username: {}", username);

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
    }
}
