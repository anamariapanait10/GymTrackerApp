package com.unibuc.gymtrackrapp.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("h2") // or "mysql"
public class ExerciseTest {

    @Autowired
    private TestEntityManager entityManager;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testPersistAndLoadExercise() {
        // Create MuscleGroup
        MuscleGroup group = new MuscleGroup();
        group.setName("Chest");

        entityManager.persist(group);

        // Create Exercise
        Exercise exercise = new Exercise();
        exercise.setName("Bench Press");
        exercise.setDescription("Chest press using barbell");
        exercise.setEquipment("Barbell");
        exercise.setDifficulty("Medium");
        exercise.getMuscleGroups().add(group);

        // Persist Exercise
        Exercise saved = entityManager.persistFlushFind(exercise);

        // Assertions
        assertNotNull(saved.getId());
        assertEquals("Bench Press", saved.getName());
        assertEquals(1, saved.getMuscleGroups().size());
        MuscleGroup relatedGroup = saved.getMuscleGroups().iterator().next();
        assertEquals("Chest", relatedGroup.getName());
    }

    @Test
    public void testExerciseValidationConstraints() {
        Exercise invalidExercise = new Exercise();
        invalidExercise.setName("");  // Violates @NotBlank
        invalidExercise.setDescription("");  // Violates @NotBlank
        invalidExercise.setEquipment("");  // Violates @NotBlank
        invalidExercise.setMuscleGroups(new HashSet<>());  // Violates @NotEmpty

        Set<ConstraintViolation<Exercise>> violations = validator.validate(invalidExercise);

        assertEquals(4, violations.size());

        List<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertTrue(messages.contains("Name is required"));
        assertTrue(messages.contains("Description is required"));
        assertTrue(messages.contains("Equipment is required"));
        assertTrue(messages.contains("At least one muscle group must be selected"));
    }

    @Test
    public void testNameMaxLengthValidation() {
        Exercise exercise = new Exercise();
        exercise.setName("a".repeat(101)); // Over 100 chars
        exercise.setDescription("Valid description");
        exercise.setEquipment("Valid equipment");

        MuscleGroup group = new MuscleGroup();
        group.setName("Back");
        entityManager.persist(group);

        exercise.getMuscleGroups().add(group);

        Set<ConstraintViolation<Exercise>> violations = validator.validate(exercise);

        assertEquals(1, violations.size());
        assertEquals("Name must be at most 100 characters", violations.iterator().next().getMessage());
    }
}
