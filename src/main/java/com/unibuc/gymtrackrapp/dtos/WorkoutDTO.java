package com.unibuc.gymtrackrapp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class WorkoutDTO {
    @NotBlank
    private String name;

    @Size(max = 500)
    private String description;
}