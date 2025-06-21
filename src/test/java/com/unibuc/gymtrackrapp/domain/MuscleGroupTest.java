package com.unibuc.gymtrackrapp.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("h2")
public class MuscleGroupTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testPersistAndLoadMuscleGroup() {
        MuscleGroup group = new MuscleGroup();
        group.setName("Back");

        MuscleGroup saved = entityManager.persistFlushFind(group);

        assertNotNull(saved.getId());
        assertEquals("Back", saved.getName());
        assertTrue(saved.getExercises().isEmpty());
    }

    @Test
    public void testMuscleGroupExerciseRelationship() {
        // Create MuscleGroup
        MuscleGroup chest = new MuscleGroup();
        chest.setName("Chest");

        // Create Exercise
        Exercise benchPress = new Exercise();
        benchPress.setName("Bench Press");
        benchPress.setDescription("Chest press with barbell");
        benchPress.setDifficulty("Medium");
        benchPress.setEquipment("Barbell");

        // Set up relationship
        chest.getExercises().add(benchPress);
        benchPress.getMuscleGroups().add(chest); // important to maintain both sides

        // Persist both
        entityManager.persist(benchPress); // persist owning side first
        entityManager.persist(chest);
        entityManager.flush();

        // Reload and assert
        MuscleGroup persisted = entityManager.find(MuscleGroup.class, chest.getId());
        assertEquals(1, persisted.getExercises().size());
        Exercise persistedExercise = persisted.getExercises().iterator().next();
        assertEquals("Bench Press", persistedExercise.getName());
    }
}