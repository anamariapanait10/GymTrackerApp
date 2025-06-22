package com.unibuc.gymtrackrapp.repositories;

import com.unibuc.gymtrackrapp.domain.Exercise;
import com.unibuc.gymtrackrapp.domain.WorkoutSet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface WorkoutSetRepository extends ReactiveMongoRepository<WorkoutSet, UUID> {

    Flux<WorkoutSet> findAllByExercise(Exercise exercise);
}
