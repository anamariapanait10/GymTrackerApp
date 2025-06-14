package com.unibuc.gymtrackrapp.repositories;

import com.unibuc.gymtrackrapp.domain.WorkoutSession;
import com.unibuc.gymtrackrapp.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, UUID> {

    List<WorkoutSession> findAllByUserEmail(String email);

    WorkoutSession findByUserEmailAndDate(String userEmail, LocalDate date);
}
