package com.unibuc.gymtrackrapp.repositories;

import com.unibuc.gymtrackrapp.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
    Optional<Exercise> findByName(String benchPress);
}