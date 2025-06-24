package com.unibuc.gymtrackerapp.service;

import com.unibuc.gymtrackerapp.domain.security.User;
import com.unibuc.gymtrackerapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserByEmail(String email) {
        return userRepository.findByUsername(email).orElse(null);
    }
}
