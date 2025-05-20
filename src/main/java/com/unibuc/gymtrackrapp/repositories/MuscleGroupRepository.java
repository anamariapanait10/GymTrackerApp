package com.unibuc.gymtrackrapp.repositories;

import com.unibuc.gymtrackrapp.domain.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, UUID> {
}
