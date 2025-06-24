package com.unibuc.gymtrackerapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutSetDTO {
    private UUID exerciseId;
    private Double weight;
    private Integer reps;
}
