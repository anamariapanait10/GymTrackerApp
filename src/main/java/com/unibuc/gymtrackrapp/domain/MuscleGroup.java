package com.unibuc.gymtrackrapp.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class MuscleGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "muscleGroups")
    private Set<Exercise> exercises = new HashSet<>();
}