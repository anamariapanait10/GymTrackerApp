package com.unibuc.gymtrackrapp.services;

import com.unibuc.gymtrackrapp.domain.MuscleGroup;
import com.unibuc.gymtrackrapp.domain.Workout;
import com.unibuc.gymtrackrapp.dtos.ExerciseDTO;
import com.unibuc.gymtrackrapp.dtos.WorkoutCreateDTO;
import com.unibuc.gymtrackrapp.dtos.WorkoutDTO;
import com.unibuc.gymtrackrapp.dtos.WorkoutSetDTO;
import com.unibuc.gymtrackrapp.repositories.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Workout getWorkout(UUID id) {
        return workoutRepository.findById(id).orElse(null);
    }

    public Page<Workout> findAll(Pageable pageable) {
        return workoutRepository.findAll(pageable);
    }

//    public Page<WorkoutCreateDTO> findAll(Pageable pageable) {
//        return workoutRepository.findAll(pageable).map(workout -> new WorkoutCreateDTO(workout.getId(), workout.getName(), workout.getDescription(),
//                workout.getSets().stream()
//                        .map(workoutSet -> new WorkoutSetDTO(workoutSet.getExercise().getId(), workoutSet.getWeight(), workoutSet.getReps())).collect(Collectors.toList())));
//    }

    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    public void deleteWorkout(UUID id) {
        workoutRepository.deleteById(id);
    }
}
