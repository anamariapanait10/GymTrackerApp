package com.unibuc.gymtrackrapp.repositories;

import com.unibuc.gymtrackrapp.domain.Exercise;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ExerciseRepository extends ReactiveMongoRepository<Exercise, UUID> {
    Mono<Exercise> findByName(String benchPress);
}