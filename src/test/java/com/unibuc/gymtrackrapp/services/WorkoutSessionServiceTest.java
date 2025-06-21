package com.unibuc.gymtrackrapp.services;

import com.unibuc.gymtrackrapp.domain.WorkoutSession;
import com.unibuc.gymtrackrapp.dtos.WorkoutSessionDTO;
import com.unibuc.gymtrackrapp.repositories.WorkoutSessionRepository;
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
public class WorkoutSessionServiceTest {

    @InjectMocks
    WorkoutSessionService workoutSessionService;

    @Mock
    WorkoutSessionRepository workoutSessionRepository;

    @Test
    public void findWorkoutSessions() {
        List<WorkoutSession> workoutSessionList = new ArrayList<>();
        WorkoutSession workoutSession = new WorkoutSession();
        workoutSessionList.add(workoutSession);

        when(workoutSessionRepository.findAll()).thenReturn(workoutSessionList);
        List<WorkoutSession> workoutSessions = workoutSessionService.getAllSessions();

        assertEquals(1, workoutSessions.size());
        verify(workoutSessionRepository, times(1)).findAll();
    }
}
