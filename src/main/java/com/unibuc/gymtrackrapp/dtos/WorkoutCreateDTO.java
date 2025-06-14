
package com.unibuc.gymtrackrapp.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Data
public class WorkoutCreateDTO {
    private String name;
    private String description;
    private List<WorkoutSetDTO> sets;
}

