package com.unibuc.gymtrackrapp.services.security;

import com.unibuc.gymtrackrapp.domain.security.Authority;
import com.unibuc.gymtrackrapp.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("mongodb")
public class UserDetailsService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("Username not found: " + email)))
                .map(user -> {
                    log.info(user.toString());
                    return org.springframework.security.core.userdetails.User
                            .withUsername(user.getEmail())
                            .password(user.getPassword())
                            .authorities(getAuthorities(user.getAuthorities()))
                            .accountExpired(!user.getAccountNonExpired())
                            .accountLocked(!user.getAccountNonLocked())
                            .credentialsExpired(!user.getCredentialsNonExpired())
                            .disabled(!user.getEnabled())
                            .build();
                });
    }

    private Set<SimpleGrantedAuthority> getAuthorities(Set<Authority> authorities) {
        if (authorities == null || authorities.isEmpty()) {
            return Set.of();
        }
        return authorities.stream()
                .map(Authority::getRole)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}