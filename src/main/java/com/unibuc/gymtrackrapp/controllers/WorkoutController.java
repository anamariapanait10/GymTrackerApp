package com.unibuc.gymtrackrapp.controllers;

import com.unibuc.gymtrackrapp.config.Log;
import com.unibuc.gymtrackrapp.domain.Workout;
import com.unibuc.gymtrackrapp.domain.WorkoutSet;
import com.unibuc.gymtrackrapp.dtos.WorkoutCreateDTO;
import com.unibuc.gymtrackrapp.dtos.WorkoutDTO;
import com.unibuc.gymtrackrapp.exceptions.ResourceNotFoundException;
import com.unibuc.gymtrackrapp.services.ExerciseService;
import com.unibuc.gymtrackrapp.services.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String saveWorkout(@Valid @ModelAttribute WorkoutCreateDTO workoutDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("exercises", exerciseService.getAllExercises());
            return "create-workout";
        }

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
        return "redirect:/workouts";
    }

    @GetMapping
    public String listWorkouts(Pageable pageable, Model model) {
        if (pageable.getPageSize() > 10 || pageable.getPageNumber() < 0)
            pageable = Pageable.ofSize(10).withPage(0);

        Page<Workout> workouts = workoutService.findAll(pageable);

        model.addAttribute("workouts", workouts);
        model.addAttribute("pageSize", pageable.getPageSize());
        model.addAttribute("pageNumber", pageable.getPageNumber());

        return "workouts";
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteWorkout(@PathVariable UUID id) {
        try {
            workoutService.deleteWorkout(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            throw ex;
        }
        catch (DataIntegrityViolationException ex) {
            return ResponseEntity.badRequest().body("Cannot delete exercise as it is associated with other records.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("An unexpected error occurred while trying to delete the exercise.");
        }
    }

}