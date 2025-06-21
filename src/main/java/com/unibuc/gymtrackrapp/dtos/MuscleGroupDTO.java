package com.unibuc.gymtrackrapp.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MuscleGroupDTO {
    @NotBlank
    private String name;
}
