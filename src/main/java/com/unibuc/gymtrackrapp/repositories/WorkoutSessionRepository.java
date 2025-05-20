package com.unibuc.gymtrackrapp.repositories;

import com.unibuc.gymtrackrapp.domain.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, UUID> {
}
