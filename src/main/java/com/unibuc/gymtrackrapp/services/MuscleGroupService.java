package com.unibuc.gymtrackrapp.services;

import com.unibuc.gymtrackrapp.domain.MuscleGroup;
import com.unibuc.gymtrackrapp.repositories.MuscleGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MuscleGroupService {
    private final MuscleGroupRepository muscleGroupRepository;

    public List<MuscleGroup> getAllMuscleGroups() {
        return muscleGroupRepository.findAll();
    }

    public Optional<MuscleGroup> getMuscleGroupById(UUID id) {
        return muscleGroupRepository.findById(id);
    }

    public MuscleGroup createMuscleGroup(String name) {
        MuscleGroup muscleGroup = new MuscleGroup();
        muscleGroup.setName(name);
        return muscleGroupRepository.save(muscleGroup);
    }

    public MuscleGroup saveMuscleGroup(MuscleGroup muscleGroup) {
        return muscleGroupRepository.save(muscleGroup);
    }

    public void deleteMuscleGroup(UUID id) {
        muscleGroupRepository.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return muscleGroupRepository.existsById(id);
    }

}
