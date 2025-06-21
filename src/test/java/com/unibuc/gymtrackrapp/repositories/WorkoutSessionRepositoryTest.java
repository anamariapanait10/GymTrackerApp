package com.unibuc.gymtrackrapp.repositories;

import com.unibuc.gymtrackrapp.domain.WorkoutSession;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@ActiveProfiles("h2")
@Sql(scripts = "classpath:initial-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Slf4j
public class WorkoutSessionRepositoryTest {

    private final WorkoutSessionRepository workoutSessionRepository;

    @Autowired
    public WorkoutSessionRepositoryTest(WorkoutSessionRepository workoutSessionRepository) {
        this.workoutSessionRepository = workoutSessionRepository;
    }

    @Test
    public void findAllByUserEmail() {
        List<WorkoutSession> sessions = workoutSessionRepository.findAllByUserEmail("admin@email.com");
        assertFalse(sessions.isEmpty());

        log.info("Workout sessions for user 'admin@email.com':");
        sessions.forEach(session -> log.info("Date: {}, Status: {}", session.getDate(), session.getStatus()));
    }

    @Test
    public void findByUserEmailAndDate() {
        LocalDate date = LocalDate.of(2025, 6, 10); // Leg Day Routine
        WorkoutSession session = workoutSessionRepository.findByUserEmailAndDate("admin@email.com", date);
        assertNotNull(session);

        log.info("Workout session on {}: Workout={}, Status={}",
                session.getDate(),
                session.getWorkout().getName(),
                session.getStatus());
    }
}
