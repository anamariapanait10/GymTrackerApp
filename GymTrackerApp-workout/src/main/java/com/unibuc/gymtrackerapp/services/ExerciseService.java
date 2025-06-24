package com.unibuc.gymtrackerapp.services;

import com.unibuc.gymtrackerapp.domain.entity.Exercise;
import com.unibuc.gymtrackerapp.domain.entity.MuscleGroup;
import com.unibuc.gymtrackerapp.domain.entity.WorkoutSet;
import com.unibuc.gymtrackerapp.dtos.ExerciseDTO;
import com.unibuc.gymtrackerapp.exceptions.ResourceNotFoundException;
import com.unibuc.gymtrackerapp.repositories.ExerciseRepository;
import com.unibuc.gymtrackerapp.repositories.WorkoutRepository;
import com.unibuc.gymtrackerapp.repositories.WorkoutSetRepository;
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
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found"));

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
