package com.unibuc.gymtrackrapp.repositories;

import com.unibuc.gymtrackrapp.domain.Exercise;
import com.unibuc.gymtrackrapp.domain.WorkoutSet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("h2")
@Sql(scripts = "classpath:initial-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Slf4j
public class WorkoutSetRepositoryTest {

    private final WorkoutSetRepository workoutSetRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public WorkoutSetRepositoryTest(WorkoutSetRepository workoutSetRepository, ExerciseRepository exerciseRepository) {
        this.workoutSetRepository = workoutSetRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @Test
    public void findAllByExerciseTest() {
        Optional<Exercise> benchPress = exerciseRepository.findByName("Bench Press");
        assertTrue(benchPress.isPresent());

        // get all workoutSets using this exercise
        Set<WorkoutSet> workoutSets = workoutSetRepository.findAllByExercise(benchPress.get());
        assertFalse(workoutSets.isEmpty());

        log.info("Workout sets using 'Bench Press' exercise:");
        workoutSets.forEach(set -> log.info("Reps: {}, Weight: {}", set.getReps(), set.getWeight()));
    }

}
