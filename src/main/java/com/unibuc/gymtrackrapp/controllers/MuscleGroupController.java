package com.unibuc.gymtrackrapp.controllers;

import com.unibuc.gymtrackrapp.domain.MuscleGroup;
import com.unibuc.gymtrackrapp.dtos.MuscleGroupDTO;
import com.unibuc.gymtrackrapp.repositories.MuscleGroupRepository;
import com.unibuc.gymtrackrapp.services.MuscleGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/muscleGroups")
@RequiredArgsConstructor
public class MuscleGroupController {

    private final MuscleGroupService muscleGroupService;

    @PostMapping
    public ResponseEntity<MuscleGroup> addMuscleGroup(@Valid @RequestBody MuscleGroupDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        return ResponseEntity.ok(muscleGroupService.createMuscleGroup(dto.getName()));
    }

}
