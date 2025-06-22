package com.unibuc.gymtrackrapp.services;

import com.unibuc.gymtrackrapp.domain.Workout;
import com.unibuc.gymtrackrapp.exceptions.ResourceNotFoundException;
import com.unibuc.gymtrackrapp.repositories.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkoutService {
    private final WorkoutRepository workoutRepository;

    public Flux<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Mono<Workout> getWorkout(UUID id) {
        return workoutRepository.findById(id).orElse(null);
    }

    public Mono<Page<Workout>> findAll(Pageable pageable) {
        return workoutRepository.findAll(pageable);
    }

    public Mono<Workout> saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    public Mono<Void> deleteWorkout(UUID id) {
        if (!workoutRepository.existsById(id))
            throw new ResourceNotFoundException("Workout not found");

        workoutRepository.deleteById(id);
    }
}
