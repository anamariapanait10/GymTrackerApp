package com.unibuc.gymtrackrapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutSetDTO {
    private UUID exerciseId;
    private Double weight;
    private Integer reps;
}
