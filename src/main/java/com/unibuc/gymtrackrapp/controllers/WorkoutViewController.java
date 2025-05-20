package com.unibuc.gymtrackrapp.controllers;

import com.unibuc.gymtrackrapp.config.Log;
import com.unibuc.gymtrackrapp.domain.Workout;
import com.unibuc.gymtrackrapp.services.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/workouts")
public class WorkoutViewController {
    private final WorkoutService workoutService;

    @Log
    @GetMapping
    public String showWorkoutsForm(Model model) {
        workoutService.getAllWorkouts();
        return "calendar";
    }
}
