package com.unibuc.gymtrackrapp.controller;

import com.unibuc.gymtrackrapp.domain.Exercise;
import com.unibuc.gymtrackrapp.dtos.ExerciseDTO;
import com.unibuc.gymtrackrapp.services.ExerciseService;
import com.unibuc.gymtrackrapp.services.MuscleGroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
public class ExerciseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExerciseService exerciseService;

    @MockBean
    private MuscleGroupService muscleGroupService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void showExercisesForm_ShouldReturnFormView() throws Exception {
        when(muscleGroupService.getAllMuscleGroups()).thenReturn(List.of());

        mockMvc.perform(get("/exercises/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-exercise"))
                .andExpect(model().attributeExists("exercise"))
                .andExpect(model().attributeExists("muscleGroups"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void saveExercise_ShouldRedirect() throws Exception {
        mockMvc.perform(post("/exercises")
                        .with(csrf())
                        .param("name", "Test Exercise")
                        .param("description", "Some description")
                        .param("difficulty", "Easy")
                        .param("equipment", "Dumbbell")
                        .param("muscleGroups", "b86f3750-4ed0-11f0-8bbb-f875a48d3498"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/exercises"));

        verify(exerciseService, times(1)).saveExercise(any(Exercise.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void listExercises_ShouldReturnSortedAndPagedExercises() throws Exception {
        Page<ExerciseDTO> mockPage = new PageImpl<>(List.of());
        when(exerciseService.findAll(any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/exercises")
                        .param("sortDir", "asc")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("exercises"))
                .andExpect(model().attributeExists("exercises"))
                .andExpect(model().attributeExists("pageSize"))
                .andExpect(model().attributeExists("pageNumber"))
                .andExpect(model().attributeExists("sortDir"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void deleteExercise_Successful() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/exercises/{id}/delete", id)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(exerciseService, times(1)).deleteExerciseById(id);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void deleteExercise_DataIntegrityViolation() throws Exception {
        UUID id = UUID.randomUUID();
        doThrow(new DataIntegrityViolationException("FK constraint")).when(exerciseService).deleteExerciseById(id);

        mockMvc.perform(delete("/exercises/{id}/delete", id)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("associated with other records")));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void deleteExercise_UnexpectedError() throws Exception {
        UUID id = UUID.randomUUID();
        doThrow(new RuntimeException("Unexpected")).when(exerciseService).deleteExerciseById(id);

        mockMvc.perform(delete("/exercises/{id}/delete", id)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("unexpected error")));
    }
}