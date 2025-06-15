package com.unibuc.gymtrackrapp.services;

import com.unibuc.gymtrackrapp.domain.Exercise;
import com.unibuc.gymtrackrapp.domain.MuscleGroup;
import com.unibuc.gymtrackrapp.domain.WorkoutSet;
import com.unibuc.gymtrackrapp.dtos.ExerciseDTO;
import com.unibuc.gymtrackrapp.repositories.ExerciseRepository;
import com.unibuc.gymtrackrapp.repositories.WorkoutRepository;
import com.unibuc.gymtrackrapp.repositories.WorkoutSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final WorkoutSetRepository workoutSetRepository;
    private final WorkoutRepository workoutRepository;

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    public Exercise saveExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public Exercise getExercise(UUID exerciseId) {
        return exerciseRepository.findById(exerciseId).orElse(null);
    }
    public Page<ExerciseDTO> findAll(Pageable pageable) {
        return exerciseRepository.findAll(pageable)
                .map(exercise -> new ExerciseDTO(exercise.getId(), exercise.getName(),
                        exercise.getDescription(),
                        exercise.getEquipment(),
                        exercise.getDifficulty(),
                        exercise.getMuscleGroups().stream()
                                .map(MuscleGroup::getName)
                                .collect(Collectors.toSet())));
    }

    public void deleteExerciseById(UUID id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        Set<WorkoutSet> allByExercise = workoutSetRepository.findAllByExercise(exercise);
        for (WorkoutSet ws : allByExercise) {
            workoutRepository.deleteById(ws.getWorkout().getId());
        }
        workoutSetRepository.deleteAll(allByExercise);

        for (MuscleGroup mg : exercise.getMuscleGroups()) {
            mg.getExercises().remove(exercise);
        }
        exercise.getMuscleGroups().clear();
        exerciseRepository.deleteById(id);
    }


}
