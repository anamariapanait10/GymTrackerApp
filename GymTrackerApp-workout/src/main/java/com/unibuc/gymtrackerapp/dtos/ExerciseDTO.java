package com.unibuc.gymtrackerapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class ExerciseDTO {
    private UUID id;
    private String name;
    private String description;
    private String equipment;
    private String difficulty;
    private Set<String> muscleGroups;
}
