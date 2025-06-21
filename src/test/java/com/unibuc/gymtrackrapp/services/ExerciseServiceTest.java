package com.unibuc.gymtrackrapp.services;

import com.unibuc.gymtrackrapp.domain.Exercise;
import com.unibuc.gymtrackrapp.dtos.ExerciseDTO;
import com.unibuc.gymtrackrapp.repositories.ExerciseRepository;
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
public class ExerciseServiceTest {

    @InjectMocks
    ExerciseService exerciseService;

    @Mock
    ExerciseRepository exerciseRepository;

    @Test
    public void findExercises() {
        List<Exercise> exercisesList = new ArrayList<>();
        Exercise exercise = new Exercise();
        exercisesList.add(exercise);

        when(exerciseRepository.findAll()).thenReturn(exercisesList);
        List<Exercise> exercises = exerciseService.getAllExercises();

        assertEquals(1, exercises.size());
        verify(exerciseRepository, times(1)).findAll();
    }

}
