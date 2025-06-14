package com.unibuc.gymtrackrapp.dtos;

import lombok.Data;
import java.util.UUID;

@Data
public class WorkoutSetDTO {
    private UUID exerciseId;
    private Double weight;
    private Integer reps;
}
