package com.unibuc.gymtrackrapp.services;

import com.unibuc.gymtrackrapp.domain.Workout;
import com.unibuc.gymtrackrapp.dtos.WorkoutDTO;
import com.unibuc.gymtrackrapp.repositories.WorkoutRepository;
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
public class WorkoutServiceTest {

    @InjectMocks
    WorkoutService workoutService;

    @Mock
    WorkoutRepository workoutRepository;

    @Test
    public void findWorkouts() {
        List<Workout> workoutsList = new ArrayList<>();
        Workout workout = new Workout();
        workoutsList.add(workout);

        when(workoutRepository.findAll()).thenReturn(workoutsList);
        List<Workout> workouts = workoutService.getAllWorkouts();

        assertEquals(1, workouts.size());
        verify(workoutRepository, times(1)).findAll();
    }
}
