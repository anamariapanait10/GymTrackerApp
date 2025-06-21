
package com.unibuc.gymtrackrapp.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutCreateDTO {
    private UUID id;
    private String name;
    private String description;
    @NotEmpty(message = "The workout must include at least one set.")
    private List<WorkoutSetDTO> sets;
}

