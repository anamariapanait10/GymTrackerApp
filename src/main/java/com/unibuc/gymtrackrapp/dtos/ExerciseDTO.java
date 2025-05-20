package com.unibuc.gymtrackrapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ExerciseDTO {
    private String name;
    private String description;
    private String type;
    private String equipment;
    private String targetMuscle;
    private String difficultyLevel;
    private String imageUrl;
}
