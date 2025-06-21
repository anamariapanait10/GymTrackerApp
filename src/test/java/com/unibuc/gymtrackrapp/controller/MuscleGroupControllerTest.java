package com.unibuc.gymtrackrapp.controller;

import com.unibuc.gymtrackrapp.domain.MuscleGroup;
import com.unibuc.gymtrackrapp.dtos.MuscleGroupDTO;
import com.unibuc.gymtrackrapp.services.MuscleGroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
public class MuscleGroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MuscleGroupService muscleGroupService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void addMuscleGroup_ShouldCreateMuscleGroup() throws Exception {
        MuscleGroupDTO dto = new MuscleGroupDTO();
        dto.setName("Shoulders");

        MuscleGroup savedGroup = new MuscleGroup();
        savedGroup.setId(UUID.randomUUID());
        savedGroup.setName("Shoulders");

        when(muscleGroupService.createMuscleGroup("Shoulders")).thenReturn(savedGroup);

        mockMvc.perform(post("/api/muscleGroups")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": "Shoulders"
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Shoulders"));

        verify(muscleGroupService, times(1)).createMuscleGroup("Shoulders");
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void addMuscleGroup_WithValidationError_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/muscleGroups")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": ""
                            }
                        """))
                .andExpect(status().isBadRequest());
    }
}
