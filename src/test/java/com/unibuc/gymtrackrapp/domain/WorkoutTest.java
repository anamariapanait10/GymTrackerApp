package com.unibuc.gymtrackrapp.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@ActiveProfiles("h2")
public class WorkoutTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testPersistWorkoutWithSetsAndSessions() {
        Workout workout = new Workout();
        workout.setName("Full Body Routine");
        workout.setDescription("Covers all major muscle groups");

        WorkoutSet set = new WorkoutSet();
        set.setWorkout(workout);
        set.setWeight(80.0);
        set.setReps(10);

        WorkoutSession session = new WorkoutSession();
        session.setWorkout(workout);
        session.setDate(LocalDate.of(2025, 6, 21));

        workout.getSets().add(set);
        workout.getSessions().add(session);

        Workout saved = entityManager.persistFlushFind(workout);

        assertNotNull(saved.getId());
        assertEquals(1, saved.getSets().size());
        assertEquals(1, saved.getSessions().size());

        WorkoutSet savedSet = saved.getSets().get(0);
        WorkoutSession savedSession = saved.getSessions().get(0);

        assertEquals(80.0, savedSet.getWeight());
        assertEquals(10, savedSet.getReps());
        assertEquals(LocalDate.of(2025, 6, 21), savedSession.getDate());

        assertNotNull(savedSet.getWorkout());
        assertNotNull(savedSession.getWorkout());
    }

    @Test
    public void testCascadeDeleteWorkout() {
        Workout workout = new Workout();
        workout.setName("Leg Day");
        workout.setDescription("Focus on squats");

        WorkoutSet set = new WorkoutSet();
        set.setWorkout(workout);
        set.setWeight(100.0);
        set.setReps(8);
        workout.getSets().add(set);

        WorkoutSession session = new WorkoutSession();
        session.setWorkout(workout);
        session.setDate(LocalDate.of(2025, 7, 1));
        workout.getSessions().add(session);

        Workout persisted = entityManager.persistFlushFind(workout);
        UUID workoutId = persisted.getId();

        entityManager.remove(persisted);
        entityManager.flush();

        Workout found = entityManager.find(Workout.class, workoutId);
        assertNull(found);
    }
}