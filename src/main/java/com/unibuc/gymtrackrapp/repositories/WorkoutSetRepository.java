package com.unibuc.gymtrackrapp.repositories;

import com.unibuc.gymtrackrapp.domain.WorkoutSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutSetRepository extends JpaRepository<WorkoutSet, UUID> {
}
