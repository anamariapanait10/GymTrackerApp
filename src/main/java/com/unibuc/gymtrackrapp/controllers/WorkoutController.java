package com.unibuc.gymtrackrapp.controllers;

import com.unibuc.gymtrackrapp.config.Log;
import com.unibuc.gymtrackrapp.domain.Workout;
import com.unibuc.gymtrackrapp.domain.WorkoutSet;
import com.unibuc.gymtrackrapp.dtos.WorkoutCreateDTO;
import com.unibuc.gymtrackrapp.services.ExerciseService;
import com.unibuc.gymtrackrapp.services.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;
    private final ExerciseService exerciseService;

    @Log
    @GetMapping( "/create")
    public String showWorkoutForm(Model model) {
        model.addAttribute("workoutCreateDTO", new WorkoutCreateDTO());
        model.addAttribute("exercises", exerciseService.getAllExercises());

        return "create-workout";
    }

    @PostMapping
    public String saveWorkout(@ModelAttribute WorkoutCreateDTO workoutDTO) {
        Workout workout = new Workout();
        workout.setName(workoutDTO.getName());
        workout.setDescription(workoutDTO.getDescription());
        
        List<WorkoutSet> sets = workoutDTO.getSets().stream()
            .map(setDTO -> {
                WorkoutSet set = new WorkoutSet();
                set.setWorkout(workout);
                set.setExercise(exerciseService.getExercise(setDTO.getExerciseId()));
                set.setWeight(setDTO.getWeight());
                set.setReps(setDTO.getReps());
                return set;
            })
            .collect(Collectors.toList());
        
        workout.setSets(sets);
        workoutService.saveWorkout(workout);
        return "redirect:/sessions";
    }

}