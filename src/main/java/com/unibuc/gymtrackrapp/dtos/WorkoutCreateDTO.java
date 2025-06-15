
package com.unibuc.gymtrackrapp.dtos;

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
    private List<WorkoutSetDTO> sets;
}

