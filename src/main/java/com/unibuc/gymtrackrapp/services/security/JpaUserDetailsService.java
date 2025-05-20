package com.unibuc.gymtrackrapp.services.security;

import com.unibuc.gymtrackrapp.domain.security.Authority;
import com.unibuc.gymtrackrapp.domain.security.User;
import com.unibuc.gymtrackrapp.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("mysql")
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user;

        Optional<User> userOpt= userRepository.findByEmail(email);
        if (userOpt.isPresent())
            user = userOpt.get();
        else
            throw new UsernameNotFoundException("Username: " + email);

        log.info(user.toString());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),user.getEnabled(), user.getAccountNonExpired(),
                user.getCredentialsNonExpired(),user.getAccountNonLocked(),
                getAuthorities(user.getAuthorities()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Authority> authorities) {
        if (authorities == null){
            return new HashSet<>();
        } else if (authorities.size() == 0){
            return new HashSet<>();
        }
        else{
            return authorities.stream()
                    .map(Authority::getRole)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
    }
}