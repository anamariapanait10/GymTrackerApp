package com.unibuc.gymtrackerapp.service;

import com.unibuc.gymtrackerapp.domain.Goal;
import com.unibuc.gymtrackerapp.exceptions.ResourceNotFoundException;
import com.unibuc.gymtrackerapp.repositories.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;

    public Goal createGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    @Transactional
    public Goal updateGoal(Goal goal) {
        return goalRepository.save(goal);
    }


    public List<Goal> getAll() {
        return goalRepository.findAll();
    }

    public Goal getGoalById(UUID id) {
        return goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with id: " + id));
    }

    @Transactional
    public void deleteGoal(UUID id) {

        if (!goalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete. Goal not found with id: " + id);
        }
        goalRepository.deleteGoalById(id);
    }
}
