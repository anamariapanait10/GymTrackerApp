package com.unibuc.gymtrackerapp.repositories;

import com.unibuc.gymtrackerapp.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, UUID> {

    Authority findByRole(String role);
}
