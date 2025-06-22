package com.unibuc.gymtrackrapp.repositories;

import com.unibuc.gymtrackrapp.domain.Workout;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface WorkoutRepository extends ReactiveMongoRepository<Workout, UUID> {
}
