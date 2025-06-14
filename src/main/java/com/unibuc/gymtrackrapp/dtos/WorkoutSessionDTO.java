package com.unibuc.gymtrackrapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class WorkoutSessionDTO {
    private String workoutName;
    private String date;
    private String notes;
    private String exercises;
}
