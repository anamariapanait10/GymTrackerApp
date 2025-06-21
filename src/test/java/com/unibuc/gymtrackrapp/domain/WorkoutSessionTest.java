package com.unibuc.gymtrackrapp.domain;

import com.unibuc.gymtrackrapp.domain.security.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("h2") // or "mysql" based on your test profile
public class WorkoutSessionTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testPersistAndLoadWorkoutSession() {
        // Create User
        User user = new User();
        user.setEmail("test@email.com");
        user.setPassword("password");

        // Create Workout
        Workout workout = new Workout();
        workout.setName("Leg Day");
        workout.setDescription("Focus on squats and lunges");

        // Persist User and Workout first (foreign key dependencies)
        entityManager.persist(user);
        entityManager.persist(workout);

        // Create WorkoutSession
        WorkoutSession session = new WorkoutSession();
        session.setDate(LocalDate.of(2025, 6, 21));
        session.setNotes("Felt strong");
        session.setStatus(SessionStatus.COMPLETED);
        session.setUser(user);
        session.setWorkout(workout);

        // Persist WorkoutSession
        WorkoutSession saved = entityManager.persistFlushFind(session);

        // Assertions
        assertNotNull(saved.getId());
        assertEquals(LocalDate.of(2025, 6, 21), saved.getDate());
        assertEquals("Felt strong", saved.getNotes());
        assertEquals(SessionStatus.COMPLETED, saved.getStatus());
        assertNotNull(saved.getUser());
        assertEquals("test@email.com", saved.getUser().getEmail());
        assertNotNull(saved.getWorkout());
        assertEquals("Leg Day", saved.getWorkout().getName());
    }
}

