package com.unibuc.gymtrackerapp.service;

import com.unibuc.gymtrackerapp.domain.security.User;
import com.unibuc.gymtrackerapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
