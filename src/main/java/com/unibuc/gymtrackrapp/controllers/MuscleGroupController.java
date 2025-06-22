package com.unibuc.gymtrackrapp.controllers;

import com.unibuc.gymtrackrapp.dtos.MuscleGroupDTO;
import com.unibuc.gymtrackrapp.services.MuscleGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/muscleGroups")
@RequiredArgsConstructor
public class MuscleGroupController {

    private final MuscleGroupService muscleGroupService;

    @PostMapping
    public Mono<ResponseEntity<Object>> addMuscleGroup(@Valid @RequestBody MuscleGroupDTO dto) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        return ResponseEntity.ok(muscleGroupService.createMuscleGroup(dto.getName()));

        return muscleGroupService.createMuscleGroup(dto.getName())
                .map(result -> ResponseEntity.ok().body(result))
                .onErrorResume(ex -> {
                    // Optional: handle specific exceptions or validation failure if custom logic is needed
                    return Mono.just(ResponseEntity.badRequest().body(ex.getMessage()));
                });
    }
}
