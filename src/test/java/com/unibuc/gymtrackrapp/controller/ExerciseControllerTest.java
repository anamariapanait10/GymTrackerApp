//package com.unibuc.gymtrackrapp.controller;
//
//import com.unibuc.gymtrackrapp.domain.Info;
//import com.unibuc.gymtrackrapp.dtos.ProductDTO;
//import com.unibuc.gymtrackrapp.exceptions.ResourceNotFoundException;
//import com.unibuc.gymtrackrapp.services.ProductService;
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Profile;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.ui.Model;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.web.servlet.function.RequestPredicates.contentType;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("h2")
//public class ExerciseControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ExerciseService exerciseService;
//
//    @MockBean
//    private MuscleGroupService muscleGroupService;
//
//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void showExercisesForm_ShouldReturnFormView() throws Exception {
//        when(muscleGroupService.getAllMuscleGroups()).thenReturn(List.of());
//
//        mockMvc.perform(get("/exercises/create"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("create-exercise"))
//                .andExpect(model().attributeExists("exercise"))
//                .andExpect(model().attributeExists("muscleGroups"));
//    }
//
//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void saveExercise_ShouldRedirect() throws Exception {
//        mockMvc.perform(post("/exercises")
//                        .with(csrf())
//                        .param("name", "Test Exercise")
//                        .param("description", "Some description")
//                        .param("difficulty", "Easy")
//                        .param("equipment", "Dumbbell"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/exercises"));
//
//        verify(exerciseService, times(1)).saveExercise(any(Exercise.class));
//    }
//
//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void listExercises_ShouldReturnSortedAndPagedExercises() throws Exception {
//        Page<ExerciseDTO> mockPage = new PageImpl<>(List.of());
//        when(exerciseService.findAll(any(Pageable.class))).thenReturn(mockPage);
//
//        mockMvc.perform(get("/exercises")
//                        .param("sortDir", "asc")
//                        .param("page", "0")
//                        .param("size", "5"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("exercises"))
//                .andExpect(model().attributeExists("exercises"))
//                .andExpect(model().attributeExists("pageSize"))
//                .andExpect(model().attributeExists("pageNumber"))
//                .andExpect(model().attributeExists("sortDir"));
//    }
//
//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void deleteExercise_Successful() throws Exception {
//        UUID id = UUID.randomUUID();
//
//        mockMvc.perform(delete("/exercises/{id}/delete", id)
//                        .with(csrf()))
//                .andExpect(status().isOk());
//
//        verify(exerciseService, times(1)).deleteExerciseById(id);
//    }
//
//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void deleteExercise_DataIntegrityViolation() throws Exception {
//        UUID id = UUID.randomUUID();
//        doThrow(new DataIntegrityViolationException("FK constraint")).when(exerciseService).deleteExerciseById(id);
//
//        mockMvc.perform(delete("/exercises/{id}/delete", id)
//                        .with(csrf()))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string(containsString("associated with other records")));
//    }
//
//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void deleteExercise_UnexpectedError() throws Exception {
//        UUID id = UUID.randomUUID();
//        doThrow(new RuntimeException("Unexpected")).when(exerciseService).deleteExerciseById(id);
//
//        mockMvc.perform(delete("/exercises/{id}/delete", id)
//                        .with(csrf()))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string(containsString("unexpected error")));
//    }
//}