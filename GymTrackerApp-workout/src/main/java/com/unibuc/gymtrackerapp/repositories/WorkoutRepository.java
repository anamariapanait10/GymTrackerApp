package com.unibuc.gymtrackerapp.repositories;

import com.unibuc.gymtrackerapp.domain.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<Workout, UUID> {
}
