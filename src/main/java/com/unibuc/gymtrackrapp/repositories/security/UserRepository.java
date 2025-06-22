package com.unibuc.gymtrackrapp.repositories.security;

import com.unibuc.gymtrackrapp.domain.security.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, UUID> {

    Mono<User> findByEmail(String email);
}