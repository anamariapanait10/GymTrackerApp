package com.unibuc.gymtrackerapp.repositories;

import com.unibuc.gymtrackerapp.domain.entity.Exercise;
import com.unibuc.gymtrackerapp.domain.entity.WorkoutSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface WorkoutSetRepository extends JpaRepository<WorkoutSet, UUID> {

    Set<WorkoutSet> findAllByExercise(Exercise exercise);
}
