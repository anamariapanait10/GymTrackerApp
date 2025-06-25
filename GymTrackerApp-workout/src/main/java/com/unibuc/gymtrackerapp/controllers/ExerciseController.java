package com.unibuc.gymtrackerapp.controllers;

import com.unibuc.gymtrackerapp.domain.entity.Exercise;
import com.unibuc.gymtrackerapp.dtos.ExerciseDTO;
import com.unibuc.gymtrackerapp.exceptions.ResourceNotFoundException;
import com.unibuc.gymtrackerapp.services.ExerciseService;
import com.unibuc.gymtrackerapp.services.MuscleGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final MuscleGroupService muscleGroupService;

    @Value("${gymtrackerapp.gateway.base-url}")
    private String gatewayBaseUrl;

    @GetMapping( "/create")
    public String showExercisesForm(Model model) {
        model.addAttribute("exercise", new Exercise());
        model.addAttribute("muscleGroups", muscleGroupService.getAllMuscleGroups());
        return "create-exercise";
    }

    @PostMapping
    public String saveExercise(@Valid @ModelAttribute Exercise exercise, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("muscleGroups", muscleGroupService.getAllMuscleGroups());
            return "create-exercise";
        }
        exerciseService.saveExercise(exercise);
        return "redirect:" + gatewayBaseUrl + "/workout/exercises";
    }

    @GetMapping
    public String listExercises(@RequestParam(defaultValue = "asc") String sortDir, Pageable pageable, Model model) {
        if (pageable.getPageSize() > 10 || pageable.getPageNumber() < 0)
            pageable = Pageable.ofSize(10).withPage(0);

        Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<ExerciseDTO> exercises = exerciseService.findAll(pageable);
        model.addAttribute("exercises", exercises);
        model.addAttribute("pageSize", pageable.getPageSize());
        model.addAttribute("pageNumber", pageable.getPageNumber());
        model.addAttribute("sortDir", sortDir);

        return "exercises";
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteExercise(@PathVariable UUID id) {
        try {
            exerciseService.deleteExerciseById(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
          throw ex;
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.badRequest().body("Cannot delete exercise as it is associated with other records.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("An unexpected error occurred while trying to delete the exercise.");
        }

    }

}
