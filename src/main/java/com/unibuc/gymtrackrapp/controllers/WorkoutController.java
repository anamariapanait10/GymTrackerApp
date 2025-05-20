package com.unibuc.gymtrackrapp.controllers;

import com.unibuc.gymtrackrapp.domain.Workout;
import com.unibuc.gymtrackrapp.services.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping
    public List<Workout> getAll() {
        return workoutService.getAllWorkouts();
    }

    @GetMapping("/{id}")
    public Workout getById(@PathVariable UUID id) {
        return workoutService.getWorkout(id);
    }

    @PostMapping
    public Workout create(@RequestBody Workout workout) {
        return workoutService.saveWorkout(workout);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        workoutService.deleteWorkout(id);
    }
}
