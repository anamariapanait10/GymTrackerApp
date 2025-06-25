package com.unibuc.gymtrackerapp.repositories;

import com.unibuc.gymtrackerapp.domain.entity.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, UUID> {

    List<WorkoutSession> findAllByUserUsername(String email);

    WorkoutSession findByUserUsernameAndDate(String userEmail, LocalDate date);

    @Modifying
    @Transactional
    @Query(value = "UPDATE workout_session SET user_id = :userId WHERE id = :sessionId", nativeQuery = true)
    void updateUserId(@Param("sessionId") UUID sessionId, @Param("userId") UUID userId);
}
