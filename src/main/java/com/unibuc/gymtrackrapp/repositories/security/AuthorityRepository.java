package com.unibuc.gymtrackrapp.repositories.security;

import com.unibuc.gymtrackrapp.domain.security.Authority;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorityRepository extends ReactiveMongoRepository<Authority, UUID> {
}
