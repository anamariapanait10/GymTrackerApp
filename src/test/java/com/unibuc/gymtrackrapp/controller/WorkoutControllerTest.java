package com.unibuc.gymtrackrapp.controller;

import com.unibuc.gymtrackrapp.domain.Exercise;
import com.unibuc.gymtrackrapp.domain.Workout;
import com.unibuc.gymtrackrapp.services.ExerciseService;
import com.unibuc.gymtrackrapp.services.WorkoutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
public class WorkoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkoutService workoutService;

    @MockBean
    private ExerciseService exerciseService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void showWorkoutForm_ShouldReturnFormView() throws Exception {
        when(exerciseService.getAllExercises()).thenReturn(List.of());

        mockMvc.perform(get("/workouts/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-workout"))
                .andExpect(model().attributeExists("workoutCreateDTO"))
                .andExpect(model().attributeExists("exercises"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void saveWorkout_WithValidData_ShouldRedirect() throws Exception {
        UUID exerciseId = UUID.randomUUID();

        when(exerciseService.getExercise(exerciseId)).thenReturn(new Exercise());

        mockMvc.perform(post("/workouts")
                        .with(csrf())
                        .param("name", "Leg Day")
                        .param("description", "Heavy squats and deadlifts")
                        .param("sets[0].exerciseId", exerciseId.toString())
                        .param("sets[0].weight", "100.0")
                        .param("sets[0].reps", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/workouts"));

        verify(workoutService, times(1)).saveWorkout(any(Workout.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void saveWorkout_WithValidationErrors_ShouldReturnFormView() throws Exception {
        mockMvc.perform(post("/workouts")
                        .with(csrf())
                        .param("description", "Missing name") // intentionally missing "name"
                )
                .andExpect(status().isOk())
                .andExpect(view().name("create-workout"))
                .andExpect(model().attributeExists("exercises"));

        verify(workoutService, never()).saveWorkout(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void listWorkouts_ShouldReturnPagedWorkouts() throws Exception {
        when(workoutService.findAll(any(Pageable.class))).thenReturn(Page.empty());

        mockMvc.perform(get("/workouts")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("workouts"))
                .andExpect(model().attributeExists("workouts"))
                .andExpect(model().attributeExists("pageSize"))
                .andExpect(model().attributeExists("pageNumber"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void deleteWorkout_Successful() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/workouts/{id}/delete", id)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(workoutService, times(1)).deleteWorkout(id);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void deleteWorkout_DataIntegrityViolation() throws Exception {
        UUID id = UUID.randomUUID();

        doThrow(new DataIntegrityViolationException("Constraint")).when(workoutService).deleteWorkout(id);

        mockMvc.perform(delete("/workouts/{id}/delete", id)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("associated with other records")));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void deleteWorkout_UnexpectedError() throws Exception {
        UUID id = UUID.randomUUID();

        doThrow(new RuntimeException("Unexpected")).when(workoutService).deleteWorkout(id);

        mockMvc.perform(delete("/workouts/{id}/delete", id)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("unexpected error")));
    }
}