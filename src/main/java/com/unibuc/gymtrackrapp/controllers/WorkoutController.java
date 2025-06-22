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
import org.springframework.web.reactive.result.view.Rendering;
import org.thymeleaf.spring6.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;

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
    public Mono<Rendering> showWorkoutForm(Model model) {
        IReactiveDataDriverContextVariable reactiveDataDrivenMode =
                new ReactiveDataDriverContextVariable(exerciseService.getAllExercises(), 10);

        return Mono.just(Rendering.view("create-workout")
                .modelAttribute("workoutCreateDTO", new WorkoutCreateDTO())
                .modelAttribute("exercises", reactiveDataDrivenMode).build());
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
    public Mono<Rendering> listWorkouts(Pageable pageable) {
        if (pageable.getPageSize() > 10 || pageable.getPageNumber() < 0)
            pageable = Pageable.ofSize(10).withPage(0);

        Mono<Page<Workout>> workouts = workoutService.findAll(pageable);

        return Mono.just(Rendering.view("workouts")
                .modelAttribute("workouts", workouts)
                .modelAttribute("pageSize", pageable.getPageSize())
                .modelAttribute("pageNumber", pageable.getPageNumber()).build());
    }

    @DeleteMapping("/{id}/delete")
    public Mono<ResponseEntity<String>> deleteWorkout(@PathVariable UUID id) {
        return workoutService.deleteWorkout(id)
                .thenReturn(ResponseEntity.ok().<String>build())
                .onErrorResume(ResourceNotFoundException.class, ex -> Mono.error(ex))
                .onErrorResume(DataIntegrityViolationException.class,
                        ex -> Mono.just(ResponseEntity.badRequest()
                                .body("Cannot delete exercise as it is associated with other records.")))
                .onErrorResume(Exception.class,
                        ex -> Mono.just(ResponseEntity.badRequest()
                                .body("An unexpected error occurred while trying to delete the exercise.")));
    }
}