package com.unibuc.gymtrackrapp.repositories;

import com.unibuc.gymtrackrapp.domain.Goal;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface GoalRepository extends ReactiveMongoRepository<Goal, UUID> {
}
