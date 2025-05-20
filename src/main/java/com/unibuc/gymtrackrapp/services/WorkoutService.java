package com.unibuc.gymtrackrapp.services;

import com.unibuc.gymtrackrapp.domain.Workout;
import com.unibuc.gymtrackrapp.repositories.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    public void deleteWorkout(UUID id) {
        workoutRepository.deleteById(id);
    }
}
