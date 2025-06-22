package com.unibuc.gymtrackrapp.repositories;

import com.unibuc.gymtrackrapp.domain.WorkoutSession;
import com.unibuc.gymtrackrapp.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface WorkoutSessionRepository extends ReactiveMongoRepository<WorkoutSession, UUID> {

    Flux<WorkoutSession> findAllByUserEmail(String email);

    Mono<WorkoutSession> findByUserEmailAndDate(String userEmail, LocalDate date);
}
