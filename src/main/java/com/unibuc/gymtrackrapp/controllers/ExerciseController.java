package com.unibuc.gymtrackrapp.controllers;

import com.unibuc.gymtrackrapp.config.Log;
import com.unibuc.gymtrackrapp.domain.Exercise;
import com.unibuc.gymtrackrapp.domain.Workout;
import com.unibuc.gymtrackrapp.dtos.ExerciseDTO;
import com.unibuc.gymtrackrapp.repositories.MuscleGroupRepository;
import com.unibuc.gymtrackrapp.services.ExerciseService;
import com.unibuc.gymtrackrapp.services.MuscleGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
        return "redirect:/exercises";
    }

    @GetMapping
    public String listExercises(Pageable pageable, Model model) {
        if (pageable.getPageSize() > 10 || pageable.getPageNumber() < 0)
            pageable = Pageable.ofSize(10).withPage(0);

        Page<ExerciseDTO> exercises = exerciseService.findAll(pageable);
        model.addAttribute("exercises", exercises);
        model.addAttribute("pageSize", pageable.getPageSize());
        model.addAttribute("pageNumber", pageable.getPageNumber());

        return "exercises";
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteExercise(@PathVariable UUID id) {
        try {
            exerciseService.deleteExerciseById(id);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.badRequest().body("Cannot delete exercise as it is associated with other records.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("An unexpected error occurred while trying to delete the exercise.");
        }

    }

}
