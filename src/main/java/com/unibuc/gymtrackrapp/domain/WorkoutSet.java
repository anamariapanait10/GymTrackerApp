package com.unibuc.gymtrackrapp.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class WorkoutSet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer reps;
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private WorkoutSession workoutSession;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;
}
