package com.unibuc.gymtrackrapp.controllers;

import com.unibuc.gymtrackrapp.domain.Exercise;
import com.unibuc.gymtrackrapp.services.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping
    public List<Exercise> getAll() {
        return exerciseService.getAllExercises();
    }

    @PostMapping
    public Exercise create(@RequestBody Exercise exercise) {
        return exerciseService.saveExercise(exercise);
    }
}
