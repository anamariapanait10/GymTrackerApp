package com.unibuc.gymtrackrapp.services;

import com.unibuc.gymtrackrapp.domain.MuscleGroup;
import com.unibuc.gymtrackrapp.repositories.MuscleGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MuscleGroupService {
    private final MuscleGroupRepository muscleGroupRepository;

    public Flux<MuscleGroup> getAllMuscleGroups() {
        return muscleGroupRepository.findAll();
    }

    public Mono<MuscleGroup> getMuscleGroupById(UUID id) {
        return muscleGroupRepository.findById(id);
    }

    public Mono<MuscleGroup> createMuscleGroup(String name) {
        MuscleGroup muscleGroup = new MuscleGroup();
        muscleGroup.setName(name);
        return muscleGroupRepository.save(muscleGroup);
    }

    public Mono<MuscleGroup> saveMuscleGroup(MuscleGroup muscleGroup) {
        return muscleGroupRepository.save(muscleGroup);
    }

    public Mono<Void> deleteMuscleGroup(UUID id) {
        muscleGroupRepository.deleteById(id);
    }

    public Mono<Boolean> existsById(UUID id) {
        return muscleGroupRepository.existsById(id);
    }

}
