package com.unibuc.gymtrackrapp.services;

import com.unibuc.gymtrackrapp.domain.security.User;
import com.unibuc.gymtrackrapp.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
