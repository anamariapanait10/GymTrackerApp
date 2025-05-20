package com.unibuc.gymtrackrapp.repositories;

import com.unibuc.gymtrackrapp.domain.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<Workout, UUID> {
}
