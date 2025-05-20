package com.unibuc.gymtrackrapp.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private List<WorkoutSession> sessions = new ArrayList<>();

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private List<WorkoutSet> sets = new ArrayList<>();
}
