package com.unibuc.gymtrackerapp.domain.entity;

import com.unibuc.gymtrackerapp.domain.entity.security.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Setter
@Getter
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
