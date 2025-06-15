package com.unibuc.gymtrackrapp.repositories;

import com.unibuc.gymtrackrapp.domain.Exercise;
import com.unibuc.gymtrackrapp.domain.WorkoutSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface WorkoutSetRepository extends JpaRepository<WorkoutSet, UUID> {

    Set<WorkoutSet> findAllByExercise(Exercise exercise);
}
