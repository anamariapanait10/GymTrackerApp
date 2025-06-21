package com.unibuc.gymtrackrapp.services;

import com.unibuc.gymtrackrapp.domain.Exercise;
import com.unibuc.gymtrackrapp.domain.MuscleGroup;
import com.unibuc.gymtrackrapp.dtos.ExerciseDTO;
import com.unibuc.gymtrackrapp.repositories.ExerciseRepository;
import com.unibuc.gymtrackrapp.repositories.MuscleGroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MuscleGroupServiceTest {

    @InjectMocks
    MuscleGroupService muscleGroupService;

    @Mock
    MuscleGroupRepository muscleGroupRepository;

    @Test
    public void findExercises() {
        List<MuscleGroup> muscleGroupsList = new ArrayList<>();
        MuscleGroup muscleGroup = new MuscleGroup();
        muscleGroupsList.add(muscleGroup);

        when(muscleGroupRepository.findAll()).thenReturn(muscleGroupsList);
        List<MuscleGroup> exercises = muscleGroupService.getAllMuscleGroups();

        assertEquals(1, exercises.size());
        verify(muscleGroupRepository, times(1)).findAll();
    }

}
