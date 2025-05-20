package com.unibuc.gymtrackrapp.repositories;

import com.unibuc.gymtrackrapp.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GoalRepository extends JpaRepository<Goal, UUID> {
}
