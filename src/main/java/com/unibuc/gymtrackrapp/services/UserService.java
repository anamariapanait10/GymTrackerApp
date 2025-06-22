package com.unibuc.gymtrackrapp.services;

import com.unibuc.gymtrackrapp.domain.security.User;
import com.unibuc.gymtrackrapp.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Mono<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
