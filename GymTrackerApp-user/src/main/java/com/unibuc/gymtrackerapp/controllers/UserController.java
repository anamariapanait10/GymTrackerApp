package com.unibuc.gymtrackerapp.controllers;

import com.unibuc.gymtrackerapp.domain.security.User;
import com.unibuc.gymtrackerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{email}")
    public Mono<UUID> getUserByEmail(@PathVariable String email) {
        return Mono.just(userService.findByUsername(email).getId());
    }

    @GetMapping
    @PreAuthorize("hasRole('gymtracker')")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

}
