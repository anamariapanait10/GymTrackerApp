package com.unibuc.gymtrackerapp.repositories;


import com.unibuc.gymtrackerapp.domain.entity.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, UUID> {
}
