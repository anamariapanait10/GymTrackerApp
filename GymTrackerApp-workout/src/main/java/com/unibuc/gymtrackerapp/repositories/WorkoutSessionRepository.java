package com.unibuc.gymtrackerapp.repositories;

import com.unibuc.gymtrackerapp.domain.entity.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, UUID> {

    List<WorkoutSession> findAllByUserUsername(String email);

    WorkoutSession findByUserUsernameAndDate(String userEmail, LocalDate date);
}
