package com.unibuc.gymtrackrapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Equipment is required")
    private String equipment;
    private String difficulty;

    @ManyToMany
    @JoinTable(
            name = "exercise_muscle_group",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_group_id")
    )
    @NotEmpty(message = "At least one muscle group must be selected")
    private Set<MuscleGroup> muscleGroups = new HashSet<>();
}