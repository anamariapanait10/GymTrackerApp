package com.unibuc.gymtrackrapp.repositories;

import com.unibuc.gymtrackrapp.domain.MuscleGroup;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface MuscleGroupRepository extends ReactiveMongoRepository<MuscleGroup, UUID> {
}
