package com.unibuc.gymtrackrapp.domain;

import com.unibuc.gymtrackrapp.domain.security.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double targetWeight;
    private LocalDate deadline;
    private String description;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
