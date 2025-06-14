package com.unibuc.gymtrackrapp.controllers;

import com.unibuc.gymtrackrapp.config.Log;
import com.unibuc.gymtrackrapp.domain.Exercise;
import com.unibuc.gymtrackrapp.domain.Workout;
import com.unibuc.gymtrackrapp.repositories.MuscleGroupRepository;
import com.unibuc.gymtrackrapp.services.ExerciseService;
import com.unibuc.gymtrackrapp.services.MuscleGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final MuscleGroupService muscleGroupService;

    @Log
    @GetMapping( "/create")
    public String showExercisesForm(Model model) {
        model.addAttribute("exercise", new Exercise());
        model.addAttribute("muscleGroups", muscleGroupService.getAllMuscleGroups());
        return "create-exercise";
    }

    @PostMapping
    public String saveExercise(@ModelAttribute Exercise exercise) {
        exerciseService.saveExercise(exercise);
        return "redirect:/sessions";
    }
}
