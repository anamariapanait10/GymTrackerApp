package com.unibuc.gymtrackrapp.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("h2") // or "mysql" depending on your setup
public class WorkoutSetTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testPersistAndLoadWorkoutSet() {
        // Create MuscleGroup
        MuscleGroup group = new MuscleGroup();
        group.setName("Legs");

        // Create Exercise
        Exercise exercise = new Exercise();
        exercise.setName("Deadlift");
        exercise.setDescription("Lift barbell from ground to hips");
        exercise.setEquipment("Barbell");
        exercise.setDifficulty("Hard");
        exercise.setMuscleGroups(new HashSet<>(List.of(group)));

        // Create Workout
        Workout workout = new Workout();
        workout.setName("Strength Training");
        workout.setDescription("High-intensity lifting session");

        // Persist related entities first
        entityManager.persist(group);
        entityManager.persist(exercise);
        entityManager.persist(workout);

        // Create WorkoutSet
        WorkoutSet set = new WorkoutSet();
        set.setReps(5);
        set.setWeight(120.0);
        set.setExercise(exercise);
        set.setWorkout(workout);

        // Persist WorkoutSet
        WorkoutSet saved = entityManager.persistFlushFind(set);

        // Assertions
        assertNotNull(saved.getId());
        assertEquals(5, saved.getReps());
        assertEquals(120.0, saved.getWeight());
        assertNotNull(saved.getExercise());
        assertEquals("Deadlift", saved.getExercise().getName());
        assertNotNull(saved.getWorkout());
        assertEquals("Strength Training", saved.getWorkout().getName());
    }
}

