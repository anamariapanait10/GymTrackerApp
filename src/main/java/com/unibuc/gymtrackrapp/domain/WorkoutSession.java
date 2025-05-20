package com.unibuc.gymtrackrapp.domain;

import com.unibuc.gymtrackrapp.domain.security.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class WorkoutSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate date;
    private String notes;

    @Enumerated(EnumType.STRING)
    private SessionStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @OneToMany(mappedBy = "workoutSession", cascade = CascadeType.ALL)
    private List<WorkoutSet> sets = new ArrayList<>();
}